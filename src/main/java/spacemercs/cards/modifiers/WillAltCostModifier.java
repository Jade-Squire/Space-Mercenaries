package spacemercs.cards.modifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.interfaces.AlternateCardCostModifier;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import spacemercs.powers.WillPower;

public class WillAltCostModifier extends AbstractCardModifier implements AlternateCardCostModifier {
    public WillAltCostModifier() {}

    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new WillAltCostModifier();
    }

    @Override
    public int getAlternateResource(AbstractCard card) {
        if(AbstractDungeon.player.hasPower(WillPower.POWER_ID)) {
            return AbstractDungeon.player.getPower(WillPower.POWER_ID).amount * card.costForTurn;
        }
        return -1;
    }

    @Override
    public boolean prioritizeAlternateCost(AbstractCard card) {
        return true;
    }

    @Override
    public boolean canSplitCost(AbstractCard card) {
        return false;
    }

    @Override
    public int spendAlternateCost(AbstractCard card, int costToSpend) {
        if(AbstractDungeon.player.hasPower(WillPower.POWER_ID)) {
            addToBot(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, WillPower.POWER_ID, 1));
            return 0;
        }
        return costToSpend;
    }
}
