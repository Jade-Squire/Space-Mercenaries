package spacemercs.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.cards.actions.SunfireFurnaceAction;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.Cure;
import spacemercs.util.CardStats;

public class SunfireFurnace extends BaseCard {
    public static final String ID = makeID(SunfireFurnace.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private static final int CURE_STACKS = 5;
    private static final int UPG_CURE_STACKS = 3;

    public SunfireFurnace() {
        super(ID, info);

        this.isMultiDamage = true;
        setMagic(CURE_STACKS, UPG_CURE_STACKS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new Cure(p, magicNumber)));
        addToBot(new SunfireFurnaceAction(p, p));
    }
}
