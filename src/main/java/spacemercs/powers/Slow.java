package spacemercs.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import spacemercs.cards.actions.CheckForFreeze;

import static spacemercs.SpaceMercsMod.makeID;

public class Slow extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(Slow.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public Slow(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        addToBot(new CheckForFreeze(owner, owner));
    }

    public void atEndOfTurn(boolean isPlayer) {
        if(!isPlayer) {
            if(owner.hasPower(POWER_ID)) {
                addToBot(new ApplyPowerAction(owner, owner, new Slow(owner, this.amount)));
            }
        }
    }

    public void onInitialApplication() {
        addToBot(new CheckForFreeze(owner, owner));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new Slow(owner, amount);
    }
}
