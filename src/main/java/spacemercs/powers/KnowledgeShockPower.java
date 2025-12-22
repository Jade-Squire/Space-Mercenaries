package spacemercs.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import spacemercs.interfaces.OnPreDiscard;

import static spacemercs.SpaceMercsMod.makeID;

public class KnowledgeShockPower extends BasePower implements OnPreDiscard, CloneablePowerInterface {
    public static final String POWER_ID = makeID(KnowledgeShockPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public KnowledgeShockPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount;
        if(amount > 1) {
            this.description += DESCRIPTIONS[3];
        } else {
            this.description += DESCRIPTIONS[2];
        }
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        super.onCardDraw(card);
        if(card.cardID.equals(VoidCard.ID)) {
            flash();
            addToTop(new ApplyPowerAction(owner, owner, new Amp(owner, amount), amount));
            addToTop(new DrawCardAction(owner, amount));
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new KnowledgeShockPower(owner, amount);
    }
}
