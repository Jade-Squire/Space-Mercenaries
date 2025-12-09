package spacemercs.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.NegativeFeedbackPower;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class NegativeFeedback extends BaseCard {
    public static final String ID = makeID(NegativeFeedback.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    private static final int STACKS = 3;

    public NegativeFeedback() {
        super(ID, info);
        setInnate(false, true);
        setMagic(STACKS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new NegativeFeedbackPower(p, 1)));
    }
}
