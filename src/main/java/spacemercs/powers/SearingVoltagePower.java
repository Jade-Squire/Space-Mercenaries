package spacemercs.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import spacemercs.interfaces.OnIgnition;

import static spacemercs.SpaceMercsMod.makeID;

public class SearingVoltagePower extends BasePower implements OnIgnition {
    public static final String POWER_ID = makeID(SearingVoltagePower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    private static final int AMP_STACKS = 10;

    public SearingVoltagePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        amount2 = 0;
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(Scorch.POWER_ID) && source == this.owner && target != this.owner && !target.hasPower("Artifact")) {
            this.flash();
            this.addToBot(new ApplyPowerAction(target, source, new Jolt(target, amount)));
        }
    }

    @Override
    public void stackPower(int stacks) {
        if(stacks == 1) {
            amount++;
        } else {
            amount++;
            amount2++;
        }
    }

    @Override
    public void onIgnite(AbstractCreature target) {
        if(amount2 > 0) {
            flash();
            addToBot(new ApplyPowerAction(this.owner, this.owner, new Amp(this.owner, AMP_STACKS * amount2)));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        if(amount2 > 0) {
            this.description += DESCRIPTIONS[2] + (amount2 * AMP_STACKS) + DESCRIPTIONS[3];
        }
    }

    @Override
    public void onInitialApplication() {
        if(amount == 2) {
            amount = 1;
            amount2 = 1;
        }
        updateDescription();
    }

    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        if(amount2 > 0) {
            amount2 *= AMP_STACKS;
        }
        super.renderAmount(sb, x, y, c);
        if(amount2 > 0) {
            amount2 /= AMP_STACKS;
        }
    }
}
