package spacemercs.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static spacemercs.SpaceMercsMod.makeID;

public class VengeancePowerPlayer extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(VengeancePowerPlayer.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public VengeancePowerPlayer(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if(damageAmount > 0 && info.owner != this.owner) {
            addToTop(new ApplyPowerAction(info.owner, this.owner, new SuppressPower(info.owner, amount)));
            if(!info.owner.hasPower(VengeancePowerEnemy.POWER_ID)) {
                addToTop(new ApplyPowerAction(info.owner, this.owner, new VengeancePowerEnemy(info.owner, -1)));
            }
        }
        return super.onAttacked(info, damageAmount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new VengeancePowerPlayer(owner, amount);
    }
}
