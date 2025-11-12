package spacemercs.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static spacemercs.SpaceMercsMod.makeID;

public class HungerPower extends BasePower {
    public static final String POWER_ID = makeID(HungerPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public HungerPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void onCreateCard(AbstractCard card) {
        if(card.cardID.equals(VoidCard.ID)) {
            updateStacks();
        }
    }

    public void onInitialApplication() {
        updateStacks();
    }

    public void onCardDraw(AbstractCard card) {
        updateStacks();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    public void updateStacks() {
        int newAmount = 0;
        for(AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if(c.cardID.equals(VoidCard.ID)) {
                newAmount++;
            }
        }
        amount = newAmount;
        updateDescription();
    }
}
