package spacemercs.cards.common;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.cards.actions.LeechingGrabAction;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class LeechingGrab extends BaseCard {
    public static final String ID = makeID(LeechingGrab.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.ATTACK,
            AbstractCard.CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 2;
    private static final int UPG_DAMAGE = 1;
    private static final int BLOCK = 1;
    private static final int UPG_BLOCK = 1;

    public LeechingGrab() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        setBlock(BLOCK, UPG_BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new LeechingGrabAction(m, p, damage, block));
    }
}
