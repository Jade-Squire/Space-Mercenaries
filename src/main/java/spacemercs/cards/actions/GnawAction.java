package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class GnawAction extends AbstractGameAction {

    private final int energyOnUse;
    private final int damage;

    public GnawAction(AbstractCreature target, AbstractCreature owner, int energyOnUse, int damage) {
        this.setValues(target, owner);
        this.energyOnUse = energyOnUse;
        this.damage = damage;
    }

    @Override
    public void update() {
        for(int i = 0; i < energyOnUse; i++) {
            addToBot(new DamageAction(target, new DamageInfo(source, damage, DamageInfo.DamageType.NORMAL), AttackEffect.SLASH_VERTICAL));
        }
        this.isDone = true;
    }
}
