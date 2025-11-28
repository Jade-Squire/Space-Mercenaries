package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import spacemercs.powers.Jolt;

public class JoltAction extends AbstractGameAction {
    public JoltAction(AbstractCreature target, AbstractCreature owner, int amount) {
        this.setValues(target, owner, amount);
    }

    @Override
    public void update() {
        CardCrawlGame.sound.playA("ORB_LIGHTNING_EVOKE", 0.9F);
        CardCrawlGame.sound.playA("ORB_LIGHTNING_PASSIVE", -0.3F);
        addToTop(new DamageAction(target, new DamageInfo(source, amount)));
        addToTop(new VFXAction(new LightningEffect(target.hb.cX, target.hb.cY)));
        addToTop(new ReducePowerAction(source, source, Jolt.POWER_ID, amount));
        this.isDone = true;
    }
}
