package spacemercs.cards.basic;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.cards.actions.OrbitalShieldAction;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class OrbitalShield extends BaseCard {
    public static final String ID = makeID(OrbitalShield.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.ATTACK,
            AbstractCard.CardRarity.BASIC,
            CardTarget.ALL_ENEMY,
            3
    );

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 3;
    private static final int REPEAT_TIMES = 3;

    public OrbitalShield() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < REPEAT_TIMES; i++) {
            addToBot(new OrbitalShieldAction(m, p, damage));
        }
    }
}
