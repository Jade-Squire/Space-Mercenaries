package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import spacemercs.powers.Jolt;

public class OverloadAction extends AbstractGameAction {
    private final int damage;

    public OverloadAction(AbstractCreature target, AbstractCreature owner, int damage) {
        this.setValues(target, owner);
        this.damage = damage;
    }

    @Override
    public void update() {
        if(target.hasPower(Jolt.POWER_ID)) {
            addToBot(new ApplyPowerAction(target, source, new Jolt(target, target.getPower(Jolt.POWER_ID).amount)));
        }
        addToBot(new DamageAction(target, new DamageInfo(source, damage), AttackEffect.BLUNT_LIGHT));
        this.isDone = true;
    }
}
