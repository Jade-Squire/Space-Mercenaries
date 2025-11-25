package spacemercs.cards.common;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import spacemercs.cards.BaseCard;
import spacemercs.cards.actions.ExhaustAllCardsInGroupAction;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class DirePredicament extends BaseCard {
    public static final String ID = makeID(DirePredicament.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            CardTarget.SELF,
            4
    );

    private static final int HEALTH_GAIN = 30;
    private static final int BLOCK = 30;

    public DirePredicament() {
        super(ID, info);
        setMagic(HEALTH_GAIN);
        setBlock(BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractPower power : p.powers) {
            if(power.type == AbstractPower.PowerType.DEBUFF) {
                addToBot(new RemoveSpecificPowerAction(p, p, power));
            }
        }

        addToBot(new HealAction(p, p, magicNumber));
        addToBot(new GainBlockAction(p, p, block));

        if(!upgraded) {
            addToBot(new ExhaustAllCardsInGroupAction(p, p, p.hand));
        }
        addToBot(new ExhaustAllCardsInGroupAction(p, p, p.discardPile));
    }
}
