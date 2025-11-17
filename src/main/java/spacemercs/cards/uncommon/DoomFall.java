package spacemercs.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

import java.util.ArrayList;

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

    private static final int BASE_DAMAGE = 2;
    private static final int UPG_DAMAGE = 1;

    public DoomFall() {
        super(ID, info);
        setDamage(BASE_DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int repeatAmt = 0;
        ArrayList<AbstractCard> cardsToExhaust = new ArrayList<>();
        for(AbstractCard c : p.drawPile.group) {
            if(c.cardID.equals(VoidCard.ID)) {
                repeatAmt++;
                cardsToExhaust.add(c);
            }
        }

        for(int i = 0; i < repeatAmt; i++) {
            addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }

        for(AbstractCard c : cardsToExhaust) {
            addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.drawPile, true));
        }
    }
}
