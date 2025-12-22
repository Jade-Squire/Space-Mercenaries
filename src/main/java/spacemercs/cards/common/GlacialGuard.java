package spacemercs.cards.common;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.SpaceMercsCustomTags;
import spacemercs.cards.BaseCard;
import spacemercs.cards.actions.GlacialGuardAction;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class GlacialGuard extends BaseCard {
    public static final String ID = makeID(GlacialGuard.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            AbstractCard.CardTarget.SELF,
            2
    );

    private static final int BLOCK = 15;
    private static final int SLOW_ON_HIT = 1;
    // hardcoded in description, unused but kept as reminder
    private static final int SLOW_ON_BREAK = 5;

    public GlacialGuard() {
        super(ID, info);
        setBlock(BLOCK);
        setMagic(SLOW_ON_HIT);
        tags.add(SpaceMercsCustomTags.APLLIESSLOW);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new GlacialGuardAction(p, p, upgraded, 1));
    }
}
