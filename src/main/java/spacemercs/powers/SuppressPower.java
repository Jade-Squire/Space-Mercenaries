package spacemercs.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static spacemercs.SpaceMercsMod.makeID;

public class SuppressPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(SuppressPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    public SuppressPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        int weakStacks = 0;
        int vulnStacks = 0;
        if(info.output > 0) {
            if(owner.hasPower(WeakPower.POWER_ID)) {
                weakStacks = owner.getPower(WeakPower.POWER_ID).amount;
            }
            if(owner.hasPower(VulnerablePower.POWER_ID)) {
                vulnStacks = owner.getPower(VulnerablePower.POWER_ID).amount;
            }
            if(weakStacks == vulnStacks) {
                if(AbstractDungeon.cardRng.random() >= 0.5) {
                    addToTop(new ApplyPowerAction(owner, AbstractDungeon.player, new VulnerablePower(owner, 1, false)));
                } else {
                    addToTop(new ApplyPowerAction(owner, AbstractDungeon.player, new WeakPower(owner, 1, false)));
                }
            } else if (weakStacks < vulnStacks) {
                addToTop(new ApplyPowerAction(owner, AbstractDungeon.player, new WeakPower(owner, 1, false)));
            } else {
                addToTop(new ApplyPowerAction(owner, AbstractDungeon.player, new VulnerablePower(owner, 1, false)));
            }
        }
        if(owner.hasPower(POWER_ID)) {
            if(owner.getPower(POWER_ID).amount > 1) {
                addToBot(new ReducePowerAction(owner, owner, this, 1));
            } else {
                addToBot(new RemoveSpecificPowerAction(owner, owner, this));
            }
        }
        return damageAmount;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new SuppressPower(owner, amount);
    }
}
