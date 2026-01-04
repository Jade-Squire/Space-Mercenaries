package spacemercs.cards.common;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class RemoteShield extends BaseCard {
    public static final String ID = makeID(RemoteShield.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            CardTarget.SELF,
            4
    );

    private static final int BLOCK = 10;
    private static final int UPG_BLOCK = 5;
    private static final int COST_REDUCTION = 1;

    public RemoteShield() {
        super(ID, info);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(COST_REDUCTION);
        setSelfRetain(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
    }

    @Override
    public void onRetained() {
        super.onRetained();
        this.modifyCostForCombat(-magicNumber);
    }
}
