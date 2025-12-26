package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.powers.Frozen;
import spacemercs.powers.Slow;

public class CheckForFreeze extends AbstractGameAction {

    public static final int STACKS_FOR_FREEZE = 20;

    public CheckForFreeze(AbstractCreature target, AbstractCreature owner) {
        this.setValues(target, owner);
    }

    @Override
    public void update() {
        if(target.hasPower(Slow.POWER_ID)) {
            if(target.getPower(Slow.POWER_ID).amount >= STACKS_FOR_FREEZE) {
                addToTop(new ApplyPowerAction(target, target, new Frozen((AbstractMonster) target, 1)));
                addToTop(new RemoveSpecificPowerAction(target, target, Slow.POWER_ID));
            }
        }
        this.isDone = true;
    }
}
