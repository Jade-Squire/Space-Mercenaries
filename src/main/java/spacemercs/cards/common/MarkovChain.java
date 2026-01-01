package spacemercs.cards.common;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class MarkovChain extends BaseCard {
    public static final String ID = makeID(MarkovChain.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            CardTarget.SELF,
            0
    );

    private static final int BLOCK = 4;
    private static final int UPG_BLOCK = 2;
    private static final int ENERGY_GAIN = 1;

    public MarkovChain() {
        super(ID, info);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(ENERGY_GAIN);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(AbstractDungeon.cardRandomRng.randomBoolean()) {
            addToBot(new GainBlockAction(p, block));
        } else {
            addToBot(new GainEnergyAction(magicNumber));
        }
    }
}
