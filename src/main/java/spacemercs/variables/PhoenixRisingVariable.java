package spacemercs.variables;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import spacemercs.cards.common.PhoenixRising;

public class PhoenixRisingVariable extends DynamicVariable {
    @Override
    public String key() {
        return "CURE";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        return false;
    }

    @Override
    public int value(AbstractCard card) {
        return ((PhoenixRising)card).currentCure;
    }

    @Override
    public int baseValue(AbstractCard card) {
        return value(card);
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        return false;
    }
}
