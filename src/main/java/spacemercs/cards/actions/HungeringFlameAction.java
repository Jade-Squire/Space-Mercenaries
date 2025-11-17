package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import spacemercs.powers.Cure;
import spacemercs.powers.HungerPower;

public class HungeringFlameAction extends AbstractGameAction {

    public HungeringFlameAction(AbstractCreature target, AbstractCreature owner) {
        this.setValues(target, owner);
    }

    @Override
    public void update() {
        int total = 0;
        if(target.hasPower(Cure.POWER_ID)) {
            total += target.getPower(Cure.POWER_ID).amount;
            addToBot(new RemoveSpecificPowerAction(target, target, Cure.POWER_ID));
        }
        if(target.hasPower(HungerPower.POWER_ID)) {
            total += target.getPower(HungerPower.POWER_ID).amount;
            addToBot(new ReducePowerAction(target, target, HungerPower.POWER_ID, target.getPower(HungerPower.POWER_ID).amount));
        }

        if(total > 0) {
            addToBot(new ApplyPowerAction(target, target, new HungerPower(target, total/2)));
            addToBot(new ApplyPowerAction(target, target, new Cure(target, total/2)));
        }

        this.isDone = true;
    }
}
