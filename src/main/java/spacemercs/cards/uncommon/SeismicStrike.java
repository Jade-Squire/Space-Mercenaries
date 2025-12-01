package spacemercs.cards.uncommon;

import com.evacipated.cardcrawl.mod.stslib.variables.RefundVariable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.Amp;
import spacemercs.powers.Jolt;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class SeismicStrike extends BaseCard {
    public static final String ID = makeID(SeismicStrike.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            -1
    );

    private static final int DAMAGE = 2;

    public SeismicStrike() {
        super(ID, info);
        RefundVariable.setBaseValue(this, 1);
        setDamage(DAMAGE);
        tags.add(CardTags.STRIKE);
    }

    @Override
    public void applyPowers() {
        int effect = AbstractDungeon.player.energy.energy;
        if(AbstractDungeon.player.hasRelic(ChemicalX.ID)){
            effect += 2;
        }
        super.applyPowers();
        this.rawDescription = (upgraded)? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0] + damage * effect + cardStrings.EXTENDED_DESCRIPTION[1];
        initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = (upgraded)? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        int effect = AbstractDungeon.player.energy.energy;
        if(AbstractDungeon.player.hasRelic(ChemicalX.ID)){
            effect += 2;
        }
        this.rawDescription = (upgraded)? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0] + damage * effect + cardStrings.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = energyOnUse;
        if(p.hasRelic(ChemicalX.ID)){
            effect += 2;
        }
        if(effect > 0) {
            addToBot(new DamageAction(m, new DamageInfo(p, damage * effect)));
            addToBot(new ApplyPowerAction(m, p, new Jolt(m, effect * 2)));
            addToBot(new ApplyPowerAction(p, p, new Amp(p, effect * 2)));
            if(upgraded) {
                addToBot(new DrawCardAction(effect));
            }
        }
        p.energy.use(EnergyPanel.totalCount);
    }
}
