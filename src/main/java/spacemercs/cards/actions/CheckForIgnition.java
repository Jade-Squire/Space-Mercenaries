package spacemercs.cards.actions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import spacemercs.interfaces.OnIgnition;
import spacemercs.powers.ForgeMasterPower;
import spacemercs.powers.Scorch;

import java.util.Objects;

public class CheckForIgnition extends AbstractGameAction {
    private boolean firstFrame = true;
    private final boolean forced;

    private static final int DAMAGE_ON_ERUPT = 30;
    private static final int DAMAGE_OTHERS_ON_ERUPT = 15;
    private static final int DAMAGE_OTHERS_ON_ERUPT_FORGEMASTER = 30;

    public static final int STACKS_FOR_IGNITION = 10;

    public CheckForIgnition(AbstractCreature target, AbstractCreature owner, boolean forcedIgnite) {
        this.setValues(target, owner);
        this.forced = forcedIgnite;
    }
    public CheckForIgnition(AbstractCreature target, AbstractCreature owner) {
        this(target, owner, false);
    }

    @Override
    public void update() {
        if(this.forced || target.hasPower(Scorch.POWER_ID)) {
            if (this.forced || target.getPower(Scorch.POWER_ID).amount >= STACKS_FOR_IGNITION) {
                if (this.firstFrame) {
                    boolean playedMusic = false;

                    for (AbstractCreature e : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        if(!e.isDead && !e.isDeadOrEscaped()) {
                            if (playedMusic) {
                                AbstractDungeon.effectList.add(new FlashAtkImgEffect(e.hb.cX, e.hb.cY, AttackEffect.FIRE, true));
                            } else {
                                playedMusic = true;
                                AbstractDungeon.effectList.add(new FlashAtkImgEffect(e.hb.cX, e.hb.cY, AttackEffect.FIRE));
                            }
                        }
                    }
                    this.firstFrame = false;
                }
            } else {
                this.isDone = true;
            }
        }
        this.tickDuration();
        if(isDone) {
            if(this.forced || target.hasPower(Scorch.POWER_ID)) {
                if (this.forced || target.getPower(Scorch.POWER_ID).amount >= STACKS_FOR_IGNITION) {
                    for (AbstractCreature e : AbstractDungeon.getCurrRoom().monsters.monsters) {
                        DamageInfo info;
                        if (Objects.equals(e, target)) {
                            info = new DamageInfo(target, DAMAGE_ON_ERUPT, DamageInfo.DamageType.THORNS);

                        } else {
                            if(AbstractDungeon.player.hasPower(ForgeMasterPower.POWER_ID)) {
                                info = new DamageInfo(target, DAMAGE_OTHERS_ON_ERUPT_FORGEMASTER, DamageInfo.DamageType.THORNS);
                            } else {
                                info = new DamageInfo(target, DAMAGE_OTHERS_ON_ERUPT, DamageInfo.DamageType.THORNS);
                            }
                        }
                        e.tint.color = Color.RED.cpy();
                        e.tint.changeColor(Color.WHITE.cpy());
                        e.damage(info);
                        this.addToTop(new WaitAction(0.1F));
                    }
                    if(target.hasPower(Scorch.POWER_ID)) {
                        addToTop(new RemoveSpecificPowerAction(target, target, target.getPower(Scorch.POWER_ID)));
                    }
                    for(AbstractPower p : AbstractDungeon.player.powers) {
                        if(p instanceof OnIgnition) {
                            ((OnIgnition) p).onIgnite(target);
                        }
                    }
                }
            }
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
    }
}
