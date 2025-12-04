package spacemercs.cards.common;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.Amp;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class Boost extends BaseCard {
    public static final String ID = makeID(Boost.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            CardTarget.SELF,
            0
    );

    private static final int AMP = 3;
    private static final int UPG_AMP = 1;
    private static final int BLOCK = 3;
    private static final int UPG_BLOCK = 1;

    public Boost() {
        super(ID, info);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(AMP, UPG_AMP);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new Amp(p, magicNumber)));
        addToBot(new GainBlockAction(p, block));
    }
}
