package spacemercs.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.PercussiveFlamesPower;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class PercussiveFlames extends BaseCard {
    public static final String ID = makeID(PercussiveFlames.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int BURNS = 3;
    private static final int UPG_BURNS = -2;

    public PercussiveFlames() {
        super(ID, info);
        setMagic(BURNS, UPG_BURNS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new PercussiveFlamesPower(p, 1), 1));
        addToBot(new MakeTempCardInHandAction(new Burn(), magicNumber));
    }
}
