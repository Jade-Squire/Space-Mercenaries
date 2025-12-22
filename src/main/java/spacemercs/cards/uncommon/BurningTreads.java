package spacemercs.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.BurningTreadsPower;
import spacemercs.powers.Scorch;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class BurningTreads extends BaseCard {
    public static final String ID = makeID(BurningTreads.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 6;
    private static final int SCORCH_STACKS = 1;
    private static final int UPG_COST = 0;

    public BurningTreads() {
        super(ID, info);
        setDamage(DAMAGE);
        setMagic(SCORCH_STACKS);
        setCostUpgrade(UPG_COST);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new ApplyPowerAction(m, p, new Scorch(m, SCORCH_STACKS)));
        addToBot(new ApplyPowerAction(m, p, new BurningTreadsPower(m, 1)));
    }
}
