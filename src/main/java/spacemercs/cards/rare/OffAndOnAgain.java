package spacemercs.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class OffAndOnAgain extends BaseCard {
    public static final String ID = makeID(OffAndOnAgain.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    private static final int CARD_DRAW = 2;

    public OffAndOnAgain() {
        super(ID, info);
        setMagic(CARD_DRAW);
        this.returnToHand = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new DiscardAction(p, p, magicNumber, false));
        if(upgraded) {
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber)));
            addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, magicNumber)));
        }
    }
}
