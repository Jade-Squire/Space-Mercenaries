package spacemercs.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.Jolt;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class Poke extends BaseCard {
    public static final String ID = makeID(Poke.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.ATTACK,
            AbstractCard.CardRarity.COMMON,
            CardTarget.ENEMY,
            0
    );

    private static final int DRAW = 1;
    private static final int UPG_DRAW = 1;

    public Poke() {
        super(ID, info);
        setMagic(DRAW, UPG_DRAW);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(m.hasPower(Jolt.POWER_ID)) {
            addToBot(new DamageAction(m, new DamageInfo(p, m.getPower(Jolt.POWER_ID).amount), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
        addToBot(new DrawCardAction(magicNumber));
    }
}
