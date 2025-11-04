package destinychar.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import destinychar.cards.BaseCard;
import destinychar.character.Titan;
import destinychar.util.CardStats;

public class HighLift extends BaseCard {
    public static final String ID = makeID(HighLift.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Titan.Meta.CARD_COLOR,
            AbstractCard.CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            AbstractCard.CardTarget.SELF,
            1
    );

    public HighLift() {
        super(ID, info);
        setExhaust(true, false);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, 1),1));
    }
}
