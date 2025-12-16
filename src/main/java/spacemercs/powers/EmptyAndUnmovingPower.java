package spacemercs.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import spacemercs.interfaces.OnGainHunger;

import static spacemercs.SpaceMercsMod.makeID;

public class EmptyAndUnmovingPower extends BasePower implements OnGainHunger {
    public static final String POWER_ID = makeID(EmptyAndUnmovingPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public EmptyAndUnmovingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onGainHunger(int hunger) {
        if(hunger > 0) {
            addToBot(new ApplyPowerAction(owner, owner, new FrostArmor(owner, -1)));
        }
    }
}
