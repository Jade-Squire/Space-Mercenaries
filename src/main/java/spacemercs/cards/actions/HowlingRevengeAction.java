package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import spacemercs.powers.HowlingRevengePower;

public class HowlingRevengeAction extends AbstractGameAction {
    private final boolean upgraded;
    private final int amount;

    public HowlingRevengeAction(AbstractCreature target, AbstractCreature owner, boolean upgraded, int amount) {
        this.setValues(target, owner);
        this.upgraded = upgraded;
        this.amount = amount;
    }

    @Override
    public void update() {
        HowlingRevengePower powerToApply = new HowlingRevengePower(source, amount);
        if(upgraded) {
            powerToApply.upgradeAmt = amount;
        }

        if(source.hasPower(HowlingRevengePower.POWER_ID)) {
            ((HowlingRevengePower)source.getPower(HowlingRevengePower.POWER_ID)).upgradeAmt += powerToApply.upgradeAmt;
        }

        addToBot(new ApplyPowerAction(source, source, powerToApply));

        this.isDone = true;
    }
}
