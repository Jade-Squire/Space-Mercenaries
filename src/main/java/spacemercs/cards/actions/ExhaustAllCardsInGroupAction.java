package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class ExhaustAllCardsInGroupAction extends AbstractGameAction {
    private final CardGroup group;
    public ExhaustAllCardsInGroupAction(AbstractCreature target, AbstractCreature owner, CardGroup group) {
        this.setValues(target, owner);
        this.group = group;
    }

    @Override
    public void update() {
        for(AbstractCard c : this.group.group) {
            addToBot(new ExhaustSpecificCardAction(c, group));
        }
        this.isDone = true;
    }
}
