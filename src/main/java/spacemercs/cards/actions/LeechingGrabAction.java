package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class LeechingGrabAction extends AbstractGameAction {
    private final int damage;
    private final int block;
    public LeechingGrabAction(AbstractCreature target, AbstractCreature owner, int damage, int block) {
        this.setValues(target, owner);
        this.damage = damage;
        this.block = block;
    }

    @Override
    public void update() {
        int count = 0;
        for(AbstractPower p : target.powers) {
            if(p.type == AbstractPower.PowerType.DEBUFF) {
                count += p.amount;
                addToBot(new RemoveSpecificPowerAction(target, source, p));
            }
        }

        for(int i = 0; i < count; i++) {
            addToBot(new DamageAction(target, new DamageInfo(source, damage, DamageInfo.DamageType.NORMAL), AttackEffect.SLASH_VERTICAL));
        }
        for(int i = 0; i < count; i++) {
            addToBot(new GainBlockAction(source, block));
        }

        this.isDone = true;
    }
}
