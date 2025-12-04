package spacemercs.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.SturdyCircuitryPower;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class SturdyCircuitry extends BaseCard {
    public static final String ID = makeID(SturdyCircuitry.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            3
    );

    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = -1;

    public SturdyCircuitry() {
        super(ID, info);
        setMagic(MAGIC, UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new SturdyCircuitryPower(p, magicNumber), magicNumber));
    }
}
