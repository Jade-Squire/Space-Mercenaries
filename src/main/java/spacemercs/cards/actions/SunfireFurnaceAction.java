package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import spacemercs.powers.Cure;

public class SunfireFurnaceAction extends AbstractGameAction {
    public SunfireFurnaceAction(AbstractCreature target, AbstractCreature owner) {
        this.setValues(target, owner);
    }

    @Override
    public void update() {
        if(target.hasPower(Cure.POWER_ID)) {
            Cure power = (Cure)target.getPower(Cure.POWER_ID);
            power.overrideCanLoseStacks(false);
        }
        this.isDone = true;
    }
}
