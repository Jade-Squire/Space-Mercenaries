package spacemercs.cards.common;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.cards.actions.GnawAction;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

public class Gnaw extends BaseCard {
    public static final String ID = makeID(Gnaw.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            AbstractCard.CardType.ATTACK,
            AbstractCard.CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int BASE_DAMAGE = 3;
    private static final int UPG_DAMAGE = 1;

    public Gnaw() {
        super(ID, info);
        setDamage(BASE_DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GnawAction(m, p, energyOnUse, damage));
    }
}
