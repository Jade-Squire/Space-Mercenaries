package spacemercs.cards.modifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.interfaces.AlternateCardCostModifier;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.awt.*;

public class GoldCostModifier extends AbstractCardModifier implements AlternateCardCostModifier {
    private final int COST;
    private Color oldColor;

    public GoldCostModifier(int cost) {
        this.COST = cost;
    }

    @Override
    public boolean isInherent(AbstractCard card) {
        return true;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new GoldCostModifier(COST);
    }

    @Override
    public int getAlternateResource(AbstractCard card) {
        return AbstractDungeon.player.gold / COST;
    }

    @Override
    public boolean prioritizeAlternateCost(AbstractCard card) {
        return true;
    }

    @Override
    public boolean canSplitCost(AbstractCard card) {
        return false;
    }

    // does nothing since cost is -2
    @Override
    public int spendAlternateCost(AbstractCard card, int costToSpend) {
        if(AbstractDungeon.player.gold / COST >= costToSpend) {
            AbstractDungeon.player.loseGold(COST);
            return 0;
        }
        return costToSpend;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        super.onInitialApplication(card);
        oldColor = card.glowColor;
    }

    @Override
    public void onRender(AbstractCard card, SpriteBatch sb) {
        if(AbstractDungeon.player != null) {
            if (AbstractDungeon.player.gold >= COST) {
                card.glowColor = Color.GOLD;
            } else {
                card.glowColor = oldColor;
            }
        }
        super.onRender(card, sb);
    }
}
