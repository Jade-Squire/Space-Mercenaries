package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import spacemercs.powers.QuickAndRelentlessPower;

public class QuickAndRelentlessAction extends AbstractGameAction {
    private final boolean upgraded;
    private final int amount;

    public QuickAndRelentlessAction(AbstractCreature target, AbstractCreature owner, boolean upgraded, int amount) {
        this.setValues(target, owner);
        this.upgraded = upgraded;
        this.amount = amount;
    }

    @Override
    public void update() {
        QuickAndRelentlessPower powerToApply = new QuickAndRelentlessPower(source, -1);
        if(upgraded) {
            powerToApply.amount2 = amount;
        }

        if(source.hasPower(QuickAndRelentlessPower.POWER_ID)) {
            ((QuickAndRelentlessPower)source.getPower(QuickAndRelentlessPower.POWER_ID)).amount2 += powerToApply.amount2;
        }

        addToBot(new ApplyPowerAction(source, source, powerToApply));

        this.isDone = true;
    }
}
