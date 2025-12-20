package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAndPoofAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class NewPathAction extends AbstractGameAction {
    private final float startingDuration;

    public NewPathAction(AbstractCreature target, AbstractCreature owner) {
        setValues(target, owner);
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;

    }

    @Override
    public void update() {
        if (this.duration == this.startingDuration) {
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck, Math.min(3, AbstractDungeon.player.masterDeck.size()), false, "Select cards to remove.");
            AbstractDungeon.gridSelectScreen.forClarity = false;
        } else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                addToTop(new ShowCardAndPoofAction(c));
                AbstractDungeon.player.masterDeck.removeCard(c);
                boolean found = removeCardFromGroup(AbstractDungeon.player.hand, c);
                if(!found) {
                    found = removeCardFromGroup(AbstractDungeon.player.drawPile, c);
                }
                if(!found) {
                    found = removeCardFromGroup(AbstractDungeon.player.discardPile, c);
                }
                if(!found) {
                    removeCardFromGroup(AbstractDungeon.player.exhaustPile, c);
                }
            }
            addToBot(new WaitAction(0.2f));
            this.isDone = true;
        }
        this.tickDuration();
    }

    private boolean removeCardFromGroup(CardGroup cardGroup, AbstractCard card) {
        AbstractCard failsafe = null;
        for(AbstractCard c : cardGroup.group) {
            if(c.cardID.equals(card.cardID)) {
                if(c.upgraded == card.upgraded) {
                    AbstractDungeon.player.hand.removeCard(c);
                    return true;
                } else {
                    failsafe = card;
                }
            }
        }
        if(failsafe != null) {
            AbstractDungeon.player.hand.removeCard(failsafe);
            return true;
        }
        return false;
    }
}
