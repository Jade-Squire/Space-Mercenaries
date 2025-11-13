package spacemercs.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.DesolateWillPower;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class DesolateWill extends BaseCard {
    public static final String ID = makeID(DesolateWill.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public DesolateWill() {
        super(ID, info);
        setInnate(false, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new DesolateWillPower(p, -1)));
    }
}
