package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import spacemercs.powers.SmokeScreenPower;

import java.util.ArrayList;

public class SmokeScreenAction extends AbstractGameAction {
    public SmokeScreenAction(AbstractCreature target, AbstractCreature owner) {
        this.setValues(target, owner);
    }

    @Override
    public void update() {
        addToBot(new ApplyPowerAction(source, source, new SmokeScreenPower(source, 1)));
        ArrayList<AbstractCard> hand = AbstractDungeon.player.hand.group;

        for(AbstractCard c : hand) {
            if(c.costForTurn > 0) {
                c.costForTurn -= 1;
                c.isCostModified = true;
            }
        }

        this.isDone = true;
    }
}
