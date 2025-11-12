package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

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
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SLASH_VERTICAL, false));
            this.target.damage(new DamageInfo(source, damage, DamageInfo.DamageType.NORMAL));
            if(this.target.lastDamageTaken > 0) {
                addToTop(new GainBlockAction(source, this.target.lastDamageTaken));
            } else {
                addToTop(new WaitAction(0.25F));
            }
        }
        this.isDone = true;
    }
}
