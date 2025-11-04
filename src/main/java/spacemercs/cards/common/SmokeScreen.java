package spacemercs.cards.common;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

import java.util.ArrayList;

public class SmokeScreen extends BaseCard {
    public static final String ID = makeID(SmokeScreen.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            AbstractCard.CardTarget.SELF,
            2
    );

    public static int BLOCK = 12;

    public SmokeScreen() {
        super(ID, info);
        setBlock(BLOCK);
        setCostUpgrade(1);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int randIndex;
        AbstractCard selectedCard;

        addToBot(new GainBlockAction(p, block));
        ArrayList<AbstractCard> possibleCards = new ArrayList<>();
        for(AbstractCard c : p.hand.group) {
            if(c != this && c.costForTurn > 0) {
                possibleCards.add(c);
            }
        }
        if(possibleCards.isEmpty()) {
            if(p.hand.group.isEmpty()) {
                return;
            }
            for(AbstractCard c : p.hand.group) {
                if(c != this) {
                    possibleCards.add(c);
                }
            }
        }
        randIndex = (int)Math.floor(Math.random() * possibleCards.size());
        selectedCard = possibleCards.get(randIndex);
        selectedCard.setCostForTurn(selectedCard.costForTurn - 1);
    }
}
