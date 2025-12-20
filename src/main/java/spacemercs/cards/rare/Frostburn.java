package spacemercs.cards.rare;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.SpaceMercsCustomTags;
import spacemercs.cards.BaseCard;
import spacemercs.cards.modifiers.WillAltCostModifier;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.FrostArmor;
import spacemercs.powers.Scorch;
import spacemercs.powers.Slow;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class Frostburn extends BaseCard {
    public static final String ID = makeID(Frostburn.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            3
    );

    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int BASE_DAMAGE = 6;
    private static final int UPG_DAMAGE = 2;
    private static final int BASE_MAGIC = 6;
    private static final int UPG_MAGIC = 2;

    public Frostburn() {
        super(ID, info);
        setDamage(BASE_DAMAGE, UPG_DAMAGE);
        setMagic(BASE_MAGIC, UPG_MAGIC);
        CardModifierManager.addModifier(this, new WillAltCostModifier());
        tags.add(SpaceMercsCustomTags.APLLIESSLOW);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new ApplyPowerAction(m, p, new Scorch(m, magicNumber)));
        addToBot(new ApplyPowerAction(m, p, new Slow(m, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new FrostArmor(p, -1)));
    }
}
