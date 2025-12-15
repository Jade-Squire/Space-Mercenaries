package spacemercs.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.FrostArmor;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class HunkerDown extends BaseCard {
    public static final String ID = makeID(HunkerDown.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private static final int UPG_COST = 0;

    public HunkerDown() {
        super(ID, info);
        setCostUpgrade(UPG_COST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new FrostArmor(p, -1)));
    }
}
