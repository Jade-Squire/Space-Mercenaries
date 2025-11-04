package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import spacemercs.powers.Kindle;

public class CheckKindleAction extends AbstractGameAction {

    public CheckKindleAction(AbstractCreature target, AbstractCreature owner) {
        this.setValues(target, owner);
    }

    @Override
    public void update() {
        if(target.hasPower(Kindle.POWER_ID)) {
            if(target.getPower(Kindle.POWER_ID).amount <= 0) {
                addToBot(new RemoveSpecificPowerAction(target, target, Kindle.POWER_ID));
            }
        }
        this.isDone = true;
    }
}
