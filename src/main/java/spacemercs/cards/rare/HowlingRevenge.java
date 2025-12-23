package spacemercs.cards.rare;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.cards.actions.HowlingRevengeAction;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

public class HowlingRevenge extends BaseCard {
    public static final String ID = makeID(HowlingRevenge.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    private static final int HP_GAIN = 25;
    private static final int UPG_HP_GAIN = 50;

    public HowlingRevenge() {
        super(ID, info);
        setMagic(HP_GAIN, UPG_HP_GAIN);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HowlingRevengeAction(p, p, upgraded, 1));
    }
}
