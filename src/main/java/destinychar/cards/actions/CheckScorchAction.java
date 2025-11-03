package destinychar.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import destinychar.powers.Scorch;

public class CheckScorchAction extends AbstractGameAction {

    public CheckScorchAction(AbstractCreature target, AbstractCreature owner) {
        this.setValues(target, owner);
    }

    @Override
    public void update() {
        if(target.hasPower(Scorch.POWER_ID)) {
            if(target.getPower(Scorch.POWER_ID).amount <= 0) {
                addToBot(new RemoveSpecificPowerAction(target, target, Scorch.POWER_ID));
            }
        }
        this.isDone = true;
    }
}
