package spacemercs.cards.modifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static spacemercs.SpaceMercsMod.makeID;

public class ModifyCostUntilPlayedModifier extends AbstractCardModifier {
    private final int cost;
    private int oldCost = -3;
    public String ID = makeID(ModifyCostUntilPlayedModifier.class.getSimpleName());

    public ModifyCostUntilPlayedModifier(int cost) {
        this.cost = cost;
    }

    @Override
    public boolean removeOnCardPlayed(AbstractCard card) {
        return true;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        if(card.cost <= 0) {
            CardModifierManager.removeSpecificModifier(card, this, false);
            return;
        }
        oldCost = card.cost;
        card.updateCost((oldCost - cost) * -1);
    }

    @Override
    public void onRemove(AbstractCard card) {
        if(oldCost == -3) {
            return;
        }
        card.updateCost((oldCost - cost));
        card.isCostModified = false;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ModifyCostUntilPlayedModifier(cost);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
