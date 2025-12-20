package spacemercs.cards.actions;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ApplyCardModifierAction extends AbstractGameAction {
    private AbstractCard card;
    private final AbstractCardModifier modifier;
    public ApplyCardModifierAction(AbstractCard card, AbstractCardModifier modifier) {
        this.card = card;
        this.modifier = modifier;
    }

    @Override
    public void update() {
        for(int i = AbstractDungeon.player.hand.size() - 1; i >= 0; i--) {
            if(AbstractDungeon.player.hand.group.get(i).cardID.equals(card.cardID)) {
                card = AbstractDungeon.player.hand.group.get(i);
                break;
            }
        }
        CardModifierManager.addModifier(card, modifier);
        this.isDone = true;
    }
}
