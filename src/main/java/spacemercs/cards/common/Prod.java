package spacemercs.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.Jolt;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class Prod extends BaseCard {
    public static final String ID = makeID(Prod.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            CardTarget.ENEMY,
            0
    );

    private static final int JOLT = 3;
    private static final int UPG_JOLT = 1;

    public Prod() {
        super(ID, info);
        setMagic(JOLT, UPG_JOLT);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new Jolt(m, magicNumber)));
        addToBot(new DrawCardAction(1));
        addToBot(new DiscardAction(p, p, 1, false));
    }
}
