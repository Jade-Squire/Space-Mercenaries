package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import spacemercs.powers.Amp;
import spacemercs.powers.BatteryPower;

public class BatteryAction extends AbstractGameAction {
    private final boolean upgraded;

    public BatteryAction(AbstractCreature target, AbstractCreature owner, boolean upgraded) {
        this.setValues(target, owner);
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        if(source.hasPower(Amp.POWER_ID)) {
            int amt = source.getPower(Amp.POWER_ID).amount;
            addToBot(new RemoveSpecificPowerAction(source, source, Amp.POWER_ID));
            addToBot(new ApplyPowerAction(source, source, new BatteryPower(source, amt * ((upgraded)? 2 : 1))));
        }
        this.isDone = true;
    }
}
