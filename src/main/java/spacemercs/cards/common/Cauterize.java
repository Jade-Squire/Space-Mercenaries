package spacemercs.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import spacemercs.cards.BaseCard;
import spacemercs.cards.actions.CheckForEruption;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.Cure;
import spacemercs.powers.Kindle;
import spacemercs.util.CardStats;

public class Cauterize extends BaseCard {
    public static final String ID = makeID(Cauterize.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            AbstractCard.CardTarget.ENEMY,
            -1
    );

    public Cauterize() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = energyOnUse;
        if(p.hasRelic(ChemicalX.ID)){
            effect += 2;
        }
        if(effect > 0) {
            addToBot(new ApplyPowerAction(m, p, new Kindle(m, 2 * effect)));
            addToBot(new CheckForEruption(m, p));
            if(upgraded) {
                addToBot(new ApplyPowerAction(p, p, new Cure(p, 2 * effect)));
            }
        }
        p.energy.use(EnergyPanel.totalCount);
    }
}
