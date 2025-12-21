package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

public class SelectCardAndAddToDeckAction extends AbstractGameAction {
    CardGroup cardGroup;
    public SelectCardAndAddToDeckAction(CardGroup group) {
        this.cardGroup = group;
        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
    }

    @Override
    public void update() {
        if (this.duration == this.startDuration) {
            AbstractDungeon.gridSelectScreen.open(cardGroup, 1, false, "Choose a card to add to your deck.");
            AbstractDungeon.gridSelectScreen.forClarity = false;
        } else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard c = AbstractDungeon.gridSelectScreen.selectedCards.get(0).makeCopy();
            addToBot(new AddCardToDeckAction(c));
            AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, true, false));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.isDone = true;
        }

        this.tickDuration();
    }
}
