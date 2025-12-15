package spacemercs.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import spacemercs.cards.actions.GainHungerAction;
import spacemercs.interfaces.OnPreDiscard;

import static spacemercs.SpaceMercsMod.makeID;

public class KnowledgeShockPower extends BasePower implements OnPreDiscard, CloneablePowerInterface {
    public static final String POWER_ID = makeID(KnowledgeShockPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private static boolean ENDING_TURN = false;
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
    public void atStartOfTurn() {
        super.atStartOfTurn();
        ENDING_TURN = false;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        if(isPlayer) {
            ENDING_TURN = true;
        }
    }

    @Override
    public boolean onDiscard(int count) {
        if(ENDING_TURN) {
            return false;
        }
        //AbstractDungeon.player.hand.moveToHand(c);
        addToBot(new GainHungerAction(owner, owner, count * amount));
        addToBot(new DrawCardAction(count * amount));
        flash();
        return true;
    }

    @Override
    public AbstractPower makeCopy() {
        return new KnowledgeShockPower(owner, amount);
    }
}
