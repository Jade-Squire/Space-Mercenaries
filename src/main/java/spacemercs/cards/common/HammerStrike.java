package spacemercs.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.Cure;
import spacemercs.util.CardStats;

public class HammerStrike extends BaseCard {
    public static final String ID = makeID(HammerStrike.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            AbstractCard.CardType.ATTACK,
            AbstractCard.CardRarity.COMMON,
            AbstractCard.CardTarget.ENEMY,
            -1
    );

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 0;

    public HammerStrike() {
        super(ID, info);

        setDamage(DAMAGE, UPG_DAMAGE);

        tags.add(CardTags.STRIKE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int effect = energyOnUse;
        if(upgraded) {
            effect += 1;
        }
        if(p.hasRelic(ChemicalX.ID)){
            effect += 2;
        }
        addToBot(new DamageAction(m, new DamageInfo(p, effect * damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        if(effect > 0) {
            if (upgraded) {
                addToBot(new ApplyPowerAction(p, p, new Cure(p, effect * 2)));
            } else {
                addToBot(new ApplyPowerAction(p, p, new Cure(p, effect)));
            }
        }

        p.energy.use(EnergyPanel.totalCount);
    }
}
