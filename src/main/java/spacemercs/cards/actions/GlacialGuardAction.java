package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import spacemercs.powers.GlacialGuardPower;

public class GlacialGuardAction extends AbstractGameAction {
    private final boolean upgraded;
    private final int amount;

    public GlacialGuardAction(AbstractCreature target, AbstractCreature owner, boolean upgraded, int amount) {
        this.setValues(target, owner);
        this.upgraded = upgraded;
        this.amount = amount;
    }

    @Override
    public void update() {
        GlacialGuardPower powerToApply = new GlacialGuardPower(source, amount);
        if(upgraded) {
            powerToApply.shouldOnBreak = true;
            powerToApply.amount2 = amount;
        }

        if(source.hasPower(GlacialGuardPower.POWER_ID) && powerToApply.shouldOnBreak) {
            ((GlacialGuardPower)source.getPower(GlacialGuardPower.POWER_ID)).shouldOnBreak = powerToApply.shouldOnBreak;
            ((GlacialGuardPower)source.getPower(GlacialGuardPower.POWER_ID)).amount2 += powerToApply.amount2;
        }

        addToBot(new ApplyPowerAction(source, source, powerToApply));

        this.isDone = true;
    }
}
