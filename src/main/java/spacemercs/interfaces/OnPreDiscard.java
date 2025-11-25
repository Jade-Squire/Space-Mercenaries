package spacemercs.interfaces;

import com.megacrit.cardcrawl.cards.AbstractCard;

public interface OnPreDiscard {
    default boolean onDiscard(AbstractCard c) {return false;}
}
