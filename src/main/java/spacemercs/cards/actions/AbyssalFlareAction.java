package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import spacemercs.powers.Cure;

public class AbyssalFlareAction extends AbstractGameAction {
    public AbyssalFlareAction(AbstractCreature target, AbstractCreature owner) {
        this.setValues(target, owner);
    }

    @Override
    public void update() {
        this.isDone = true;
        if(!source.hasPower(Cure.POWER_ID)) {
            return;
        }
        int amount = source.getPower(Cure.POWER_ID).amount;

        addToBot(new RemoveSpecificPowerAction(source, source, Cure.POWER_ID));
        addToBot(new GainBlockAction(target, amount));
        addToBot(new GainHungerAction(target, source, amount));
    }
}
