package spacemercs.cards.modifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.interfaces.AlternateCardCostModifier;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import spacemercs.powers.WillPower;

public class WillCostModifier extends AbstractCardModifier implements AlternateCardCostModifier {
    public WillCostModifier() {}

    @Override
    public AbstractCardModifier makeCopy() {
        return new WillCostModifier();
    }

    @Override
    public int getAlternateResource(AbstractCard card) {
        if(!AbstractDungeon.player.hasPower(WillPower.POWER_ID)) {
            return -1;
        }
        return AbstractDungeon.player.getPower(WillPower.POWER_ID).amount / 2;
    }

    @Override
    public boolean prioritizeAlternateCost(AbstractCard card) {
        return false;
    }

    @Override
    public boolean canSplitCost(AbstractCard card) {
        return true;
    }

    @Override
    public int spendAlternateCost(AbstractCard abstractCard, int costToSpend) {
        int resource = -1;
        if(AbstractDungeon.player.hasPower(WillPower.POWER_ID)) {
            resource = AbstractDungeon.player.getPower(WillPower.POWER_ID).amount / 2;
        }
        if(resource > costToSpend) {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, WillPower.POWER_ID, costToSpend * 2));
            costToSpend = 0;
        } else if(resource > 0) {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, WillPower.POWER_ID, resource * 2));
            costToSpend -= resource;
        }

        return costToSpend;
    }
}
