package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import spacemercs.powers.Cure;

public class HungeringFlameAction extends AbstractGameAction {

    public HungeringFlameAction(AbstractCreature target, AbstractCreature owner) {
        this.setValues(target, owner);
    }

    @Override
    public void update() {
        int total = 0;
        if(target.hasPower(Cure.POWER_ID)) {
            total += target.getPower(Cure.POWER_ID).amount;
            addToBot(new RemoveSpecificPowerAction(target, target, Cure.POWER_ID));
        }
        total += target.currentBlock;

        if(total > 0) {
            addToBot(new RemoveAllBlockAction(target, target));
            addToBot(new GainBlockAction(target, target, total/2));
            addToBot(new ApplyPowerAction(target, target, new Cure(target, total/2)));
        }

        this.isDone = true;
    }
}
