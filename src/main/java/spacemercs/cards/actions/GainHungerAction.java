package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import spacemercs.powers.HungerPower;

public class GainHungerAction extends AbstractGameAction {
    private final int amount;

    public GainHungerAction(AbstractCreature target, AbstractCreature owner, int amount) {
        this.setValues(target, owner);
        this.amount = amount;
    }

    @Override
    public void update() {
        addToBot(new GainEnergyAction(amount));
        addToBot(new MakeTempCardInDrawPileAction(new VoidCard(), amount, true, true));
        if(!AbstractDungeon.player.hasPower(HungerPower.POWER_ID)) {
            addToBot(new ApplyPowerAction(target, target, new HungerPower(target, 0)));
        }
        this.isDone = true;
    }
}
