package spacemercs.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.Amp;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class AmplifyPotential extends BaseCard {
    public static final String ID = makeID(AmplifyPotential.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int AMP_STACKS = 2; // update card description if changed
    private static final int DRAW = 2;
    private static final int UPG_DRAW = 1;

    public AmplifyPotential() {
        super(ID, info);
        setMagic(DRAW, UPG_DRAW);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new Amp(p, AMP_STACKS)));
        addToBot(new DrawCardAction(magicNumber));
    }
}
