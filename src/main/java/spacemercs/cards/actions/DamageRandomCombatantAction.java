package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class DamageRandomCombatantAction extends AbstractGameAction {
    private final DamageInfo info;

    public DamageRandomCombatantAction(DamageInfo info, AbstractGameAction.AttackEffect effect) {
        this.info = info;
        this.actionType = ActionType.DAMAGE;
        this.attackEffect = effect;
    }

    public void update() {
        ArrayList<AbstractCreature> targets = new ArrayList<>();
        for(AbstractCreature m : AbstractDungeon.getMonsters().monsters) {
            if(!m.isDeadOrEscaped()) {
                targets.add(m);
            }
        }
        targets.add(AbstractDungeon.player);
        this.target = targets.get(AbstractDungeon.cardRandomRng.random(targets.size() - 1));
        if (this.target != null) {
            this.addToTop(new DamageAction(this.target, this.info, this.attackEffect));
        }
        this.isDone = true;
    }
}
