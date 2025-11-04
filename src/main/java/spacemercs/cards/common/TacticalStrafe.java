package spacemercs.cards.common;

import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

public class TacticalStrafe extends BaseCard {
    public static final String ID = makeID(TacticalStrafe.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            AbstractCard.CardTarget.SELF,
            0
    );

    public static final int BLOCK = 3;
    public static final int DRAW = 1;
    public static final int UPG_DRAW = 1;

    public TacticalStrafe() {
        super(ID, info);
        setBlock(BLOCK);
        setMagic(DRAW, UPG_DRAW);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
       addToBot(new GainBlockAction(p, block));
       addToBot(new DrawCardAction(magicNumber));
       addToBot(new DiscardAction(p, p, 1, false));
    }
}
