package spacemercs.cards.common;

import com.evacipated.cardcrawl.mod.stslib.actions.common.GainCustomBlockAction;
import com.evacipated.cardcrawl.mod.stslib.blockmods.BlockModifierManager;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.blockmods.HeatSinkBlockMod;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.Cure;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class HeatSink extends BaseCard {
    public static final String ID = makeID(HeatSink.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private static final int UPG_COST = 0;
    private static final int MULT = 2;

    public HeatSink() {
        super(ID, info);
        setCostUpgrade(UPG_COST);
        BlockModifierManager.addModifier(this, new HeatSinkBlockMod());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int block = 0;
        if(p.hasPower(Cure.POWER_ID)) {
            block = p.getPower(Cure.POWER_ID).amount * MULT;
            addToBot(new RemoveSpecificPowerAction(p, p, Cure.POWER_ID));
        }
        if(block > 0) {
            addToBot(new GainCustomBlockAction(this, p, block));
        }
    }
}
