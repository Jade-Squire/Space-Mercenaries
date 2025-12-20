package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.powers.Scorch;
import spacemercs.powers.Slow;
import spacemercs.powers.WillPower;

public class HotAndColdAction extends AbstractGameAction {
    public HotAndColdAction(AbstractCreature target, AbstractCreature owner) {
        setValues(target, owner);
    }

    @Override
    public void update() {
        int willGain = 0;

        for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if(m.hasPower(Scorch.POWER_ID)) {
                addToBot(new ApplyPowerAction(m, source, new Scorch(m, m.getPower(Scorch.POWER_ID).amount)));
                if(m.getPower(Scorch.POWER_ID).amount * 2 >= CheckForIgnition.STACKS_FOR_IGNITION) {
                    willGain++;
                }
            }
            if(m.hasPower(Slow.POWER_ID)) {
                addToBot(new ApplyPowerAction(m, source, new Slow(m, m.getPower(Slow.POWER_ID).amount)));
                if(m.getPower(Slow.POWER_ID).amount * 2 >= CheckForFreeze.STACKS_FOR_FREEZE) {
                    willGain++;
                }
            }
        }

        if(willGain > 0) {
            addToBot(new ApplyPowerAction(source, source, new WillPower(source, willGain)));
        }
        this.isDone = true;
    }
}
