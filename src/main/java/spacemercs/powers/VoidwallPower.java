package spacemercs.powers;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseBlockPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static spacemercs.SpaceMercsMod.makeID;

public class VoidwallPower extends BasePower implements OnLoseBlockPower {
    public static final String POWER_ID = makeID(VoidwallPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public VoidwallPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public int onLoseBlock(DamageInfo damageInfo, int i) {
        if(damageInfo.owner != this.owner) {
            addToBot(new ApplyPowerAction(damageInfo.owner, owner,new VulnerablePower(damageInfo.owner, 1, false)));
        }
        return i;
    }
}
