package spacemercs.cards.actions;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ShiverStrikeAction extends AbstractGameAction {

    public ShiverStrikeAction(AbstractCreature target, AbstractCreature owner) {
        this.setValues(target, owner);
    }

    @Override
    public void update() {
        for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if(m != target) {
                for(AbstractPower p : target.powers) {
                    if(p.type == AbstractPower.PowerType.DEBUFF && p instanceof CloneablePowerInterface) {
                        AbstractPower powerToApply = ((CloneablePowerInterface) p).makeCopy();
                        powerToApply.owner = m;
                        powerToApply.updateDescription();
                        addToBot(new ApplyPowerAction(m, source, powerToApply, powerToApply.amount));
                    }
                }
            }
        }

        this.isDone = true;
    }
}
