package spacemercs.cards.rare;

/// TEST THIS!!!



import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.KnowledgeShockPower;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class KnowledgeShock extends BaseCard {
    public static final String ID = makeID(KnowledgeShock.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            4
    );

    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int DRAW_AND_AMP = 1;
    private static final int UPG_DRAW_AND_AMP = 1;

    public KnowledgeShock() {
        super(ID, info);
        setMagic(DRAW_AND_AMP, UPG_DRAW_AND_AMP);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new KnowledgeShockPower(p, magicNumber), magicNumber));
    }
}
