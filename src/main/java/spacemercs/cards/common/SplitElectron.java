package spacemercs.cards.common;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.Jolt;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class SplitElectron extends BaseCard {
    public static final String ID = makeID(SplitElectron.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int UPG_COST = 0;

    public SplitElectron() {
        super(ID, info);
        setCostUpgrade(UPG_COST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int jolt = 0;
        if(m.hasPower(Jolt.POWER_ID)) {
            jolt = m.getPower(Jolt.POWER_ID).amount;
            addToBot(new RemoveSpecificPowerAction(m, p, Jolt.POWER_ID));
        }
        if(jolt > 0) {
            addToBot(new GainBlockAction(p, jolt));
        }
    }
}
