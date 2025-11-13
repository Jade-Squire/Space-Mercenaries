package spacemercs.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class DoomFall extends BaseCard {
    public static final String ID = makeID(DoomFall.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            3
    );

    private static final int BASE_DAMAGE = 4;
    private static final int UPG_DAMAGE = 2;

    public DoomFall() {
        super(ID, info);
        setDamage(BASE_DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int repeatAmt = 0;
        for(AbstractCard c : p.drawPile.group) {
            if(c.type.equals(CardType.STATUS)) {
                repeatAmt++;
            }
        }

        for(int i = 0; i < repeatAmt; i++) {
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
    }
}
