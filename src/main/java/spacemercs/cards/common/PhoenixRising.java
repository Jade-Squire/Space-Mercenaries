package spacemercs.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.cards.actions.PhoenixRisingAction;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.Cure;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class PhoenixRising extends BaseCard {
    public static final String ID = makeID(PhoenixRising.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private static final int BASE_CURE = 1;
    private static final int CURE_INCREASE = 1;
    private static final int UPG_COST = 0;

    public PhoenixRising() {
        super(ID, info);
        this.misc = BASE_CURE;
        this.baseMagicNumber = CURE_INCREASE;
        this.magicNumber = this.baseMagicNumber;
        this.baseBlock = this.misc;
        setCostUpgrade(UPG_COST);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new PhoenixRisingAction(this.uuid, this.magicNumber));
        this.addToBot(new ApplyPowerAction(p, p, new Cure(p, this.misc)));
    }

    @Override
    public void onLoadedMisc() {
        applyPowers();
    }

    @Override
    public void applyPowers() {
        this.baseBlock = this.misc;
        super.applyPowers();
        this.initializeDescription();
    }

    public AbstractCard makeCopy() {
        return new PhoenixRising();
    }
}
