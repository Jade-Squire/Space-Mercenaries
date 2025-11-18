package spacemercs.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface OnCreateCard {
    default void onCreateCardBeforeDrawPile(AbstractCard c) {}
    default void onCreateCardAfterDrawPile(AbstractCard c) {}
}
