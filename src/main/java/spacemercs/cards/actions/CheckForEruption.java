package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.defect.DamageAllButOneEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import spacemercs.powers.Kindle;

public class CheckForEruption extends AbstractGameAction {

    private static final int DAMAGE_ON_ERUPT = 30;
    private static final int DAMAGE_OTHERS_ON_ERUPT = 15;

    public CheckForEruption(AbstractCreature target, AbstractCreature owner) {
        this.setValues(target, owner);
    }

    @Override
    public void update() {
        if(target.hasPower(Kindle.POWER_ID)) {
            if(target.getPower(Kindle.POWER_ID).amount >= 10){
                int[] damage = new int[20];
                for(int i = 0; i < 20; i++){
                    damage[i] = DAMAGE_OTHERS_ON_ERUPT;
                }
                addToBot(new DamageAction(target, new DamageInfo(target, DAMAGE_ON_ERUPT, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
                addToBot(new DamageAllButOneEnemyAction(AbstractDungeon.player, target, damage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE));
                addToBot(new RemoveSpecificPowerAction(target, target, target.getPower(Kindle.POWER_ID)));
            }
        }
        this.isDone = true;
    }
}
