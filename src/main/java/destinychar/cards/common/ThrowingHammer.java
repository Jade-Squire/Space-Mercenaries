package destinychar.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import destinychar.cards.BaseCard;
import destinychar.cards.actions.CheckScorchAction;
import destinychar.character.Titan;
import destinychar.powers.Scorch;
import destinychar.util.CardStats;

public class ThrowingHammer extends BaseCard {
    public static final String ID = makeID(ThrowingHammer.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Titan.Meta.CARD_COLOR,
            AbstractCard.CardType.ATTACK,
            AbstractCard.CardRarity.COMMON,
            AbstractCard.CardTarget.ENEMY,
            1
    );

    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int DAMAGE = 3;
    private static final int UPG_DAMAGE = 0;

    public ThrowingHammer() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        addToBot(new ApplyPowerAction(m, p, new Scorch(m, 4)));
        addToBot(new CheckScorchAction(m, p));
        if(upgraded) {
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 1)));
        }
    }

    @Override
    public void upgrade(){
        super.upgrade();

        this.returnToHand = true;
    }
}
