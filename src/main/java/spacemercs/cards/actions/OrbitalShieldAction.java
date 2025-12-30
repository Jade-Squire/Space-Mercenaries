package spacemercs.cards.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.DamageCallbackAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class OrbitalShieldAction extends AbstractGameAction {
    private final int damage;
    public OrbitalShieldAction(AbstractCreature target, AbstractCreature owner, int damage) {
        this.setValues(target, owner);
        this.damage = damage;
    }

    @Override
    public void update() {
        this.target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        if (this.target != null) {
            addToTop(new DamageCallbackAction(this.target, new DamageInfo(this.source, damage, DamageInfo.DamageType.NORMAL), AttackEffect.SLASH_VERTICAL, unblocked -> {
                if(unblocked > 0)
                {
                    addToTop(new GainBlockAction(this.source, unblocked));
                } else {
                    addToTop(new WaitAction(0.25F));
                }}));
        }
        this.isDone = true;
    }
}
