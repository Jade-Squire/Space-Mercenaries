package spacemercs.cards.uncommon;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.variables.RefundVariable;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import spacemercs.cards.BaseCard;
import spacemercs.cards.actions.SeismicStrikeAction;
import spacemercs.cards.modifiers.WillCostModifier;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.WillPower;
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
        int effect = getTotalEffect();
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
        int effect = getTotalEffect();
        this.rawDescription = (upgraded)? cardStrings.UPGRADE_DESCRIPTION : cardStrings.DESCRIPTION;
        this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0] + damage * effect + cardStrings.EXTENDED_DESCRIPTION[1];
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SeismicStrikeAction(m, p, energyOnUse, damage, upgraded));
    }

    private int getTotalEffect() {
        int effect = EnergyPanel.totalCount;
        if(AbstractDungeon.player.hasRelic(ChemicalX.ID)){
            effect += 2;
        }
        if(AbstractDungeon.player.hasPower(WillPower.POWER_ID)) {
            if(CardModifierManager.hasModifier(this, WillCostModifier.ID)) {
                if(((WillCostModifier)CardModifierManager.getModifiers(this, WillCostModifier.ID).get(0)).shouldSpend) {
                    effect += AbstractDungeon.player.getPower(WillPower.POWER_ID).amount / 2;
                }
            }
        }
        return effect;
    }
}
