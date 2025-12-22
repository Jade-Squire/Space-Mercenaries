package spacemercs.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static spacemercs.SpaceMercsMod.makeID;

public class ChillingPastPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(ChillingPastPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public ChillingPastPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if(power.type == PowerType.DEBUFF && !power.ID.equals("Shackled") && target != this.owner && !target.hasPower("Artifact") && power.amount > 0) {
            addToTop(new ApplyPowerAction(owner, owner, new StrengthPower(owner, power.amount), power.amount));
            addToTop(new ApplyPowerAction(owner, owner, new LoseStrengthPower(owner, power.amount), power.amount));
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new ChillingPastPower(owner, amount);
    }
}
