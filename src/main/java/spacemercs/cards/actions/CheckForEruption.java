package spacemercs.cards.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import spacemercs.powers.Kindle;

import java.util.Objects;

public class CheckForEruption extends AbstractGameAction {
    private boolean firstFrame = true;

    private static final int DAMAGE_ON_ERUPT = 30;
    private static final int DAMAGE_OTHERS_ON_ERUPT = 15;

    public CheckForEruption(AbstractCreature target, AbstractCreature owner) {
        this.setValues(target, owner);
    }

    @Override
    public void update() {
        if(target.hasPower(Kindle.POWER_ID)) {
            if (target.getPower(Kindle.POWER_ID).amount >= 10) {
                if (this.firstFrame) {
                    boolean playedMusic = false;

                    for (AbstractCreature e : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        if (playedMusic) {
                            AbstractDungeon.effectList.add(new FlashAtkImgEffect(e.hb.cX, e.hb.cY, AttackEffect.FIRE, true));
                        } else {
                            playedMusic = true;
                            AbstractDungeon.effectList.add(new FlashAtkImgEffect(e.hb.cX, e.hb.cY, AttackEffect.FIRE));
                        }
                    }
                    this.firstFrame = false;
                }
            }
        }
        this.tickDuration();
        if(isDone) {
            if(target.hasPower(Kindle.POWER_ID)) {
                if (target.getPower(Kindle.POWER_ID).amount >= 10) {
                    for (AbstractCreature e : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        DamageInfo info;
                        if (Objects.equals(e, target)) {
                            info = new DamageInfo(AbstractDungeon.player, DAMAGE_ON_ERUPT, DamageInfo.DamageType.NORMAL);

                        } else {
                            info = new DamageInfo(AbstractDungeon.player, DAMAGE_OTHERS_ON_ERUPT, DamageInfo.DamageType.NORMAL);
                        }
                        e.tint.color = Color.RED.cpy();
                        e.tint.changeColor(Color.WHITE.cpy());
                        //info.applyEnemyPowersOnly(target);
                        e.damage(info);
                        this.addToTop(new WaitAction(0.1F));
                    }
                    addToBot(new RemoveSpecificPowerAction(target, target, target.getPower(Kindle.POWER_ID)));
                }
            }
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
    }
}
