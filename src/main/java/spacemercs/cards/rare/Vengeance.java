package spacemercs.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.VengeancePowerPlayer;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class Vengeance extends BaseCard {
    public static final String ID = makeID(Vengeance.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            0
    );

    private static final int STACKS = 1;
    private static final int UPG_STACKS = 1;

    public Vengeance() {
        super(ID, info);
        setMagic(STACKS, UPG_STACKS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new VengeancePowerPlayer(p, magicNumber)));
    }
}
