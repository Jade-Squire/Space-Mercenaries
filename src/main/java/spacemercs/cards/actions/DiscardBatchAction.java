package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import spacemercs.interfaces.OnPreDiscard;

import java.util.ArrayList;
import java.util.List;

public class DiscardBatchAction extends AbstractGameAction {
    private final ArrayList<AbstractCard> cardsToDiscard;

    public DiscardBatchAction(AbstractCreature target, AbstractCreature owner, List<AbstractCard> cards) {
        this.setValues(target, owner);
        this.cardsToDiscard = new ArrayList<>(cards);
    }

    public void update() {
        if(this.cardsToDiscard == null) {
            this.isDone = true;
            return;
        }

        for(AbstractPower p : AbstractDungeon.player.powers) {
            if(p instanceof OnPreDiscard) {
                if(((OnPreDiscard) p).onDiscard((this.cardsToDiscard.size()))) {
                    this.isDone = true;
                    return;
                }
            }
        }

        for(AbstractCard c : this.cardsToDiscard) {
            AbstractDungeon.player.hand.moveToDiscardPile(c);
            GameActionManager.incrementDiscard(false);
            c.triggerOnManualDiscard();
        }
        this.isDone = true;
    }
}
