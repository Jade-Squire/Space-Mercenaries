package spacemercs.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface OnCreateCard {
    default void onCreateCardBeforeDrawPile(AbstractCard c, AbstractCard src) {}
    default void onCreateCardAfterDrawPile(AbstractCard c, AbstractCard src) {}
}
