package spacemercs.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import spacemercs.cards.actions.RefundHandlerAction;

import static spacemercs.SpaceMercsMod.makeID;

public class ConsumingEnergyPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(ConsumingEnergyPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    private static final int AMOUNT_PER_STACK = 3;

    public ConsumingEnergyPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        super.onUseCard(card, action);
        int cardCost = (card.cost == -1)? card.energyOnUse : card.costForTurn;
        if(cardCost > 3) {
            flashWithoutSound();
            addToBot(new RefundHandlerAction(owner, owner, card, amount * AMOUNT_PER_STACK));
            addToTop(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        addToTop(new RemoveSpecificPowerAction(owner, owner, this));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + (amount * AMOUNT_PER_STACK) + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new ConsumingEnergyPower(owner, amount);
    }
}
