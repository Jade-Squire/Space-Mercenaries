package spacemercs.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.BlissfulIgnorancePower;
import spacemercs.powers.WillPower;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class BlissfulIgnorance extends BaseCard {
    public static final String ID = makeID(BlissfulIgnorance.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            3
    );

    private static final int UPG_COST = 2;
    private static final int BLOCK = 20;
    private static final int WILL_GAIN = 3;
    private static final int UPG_WILL_GAIN = 2;

    public BlissfulIgnorance() {
        super(ID, info);
        setCostUpgrade(UPG_COST);
        setBlock(BLOCK);
        setMagic(WILL_GAIN, UPG_WILL_GAIN);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new WillPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new BlissfulIgnorancePower(p, 1)));
    }
}
