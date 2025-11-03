package destinychar.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import destinychar.cards.BaseCard;
import destinychar.character.Titan;
import destinychar.util.CardStats;

public class HammerStrike extends BaseCard {
    public static final String ID = makeID(HammerStrike.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Titan.Meta.CARD_COLOR,
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
        if(upgraded) {
            addToBot(new DamageAction(m, new DamageInfo(p, (energyOnUse + 1) * damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        } else {
            addToBot(new DamageAction(m, new DamageInfo(p, energyOnUse * damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }

        p.energy.use(EnergyPanel.totalCount);
    }
}
