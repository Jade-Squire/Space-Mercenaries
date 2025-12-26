package spacemercs.cards.basic;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.cards.actions.GainHungerAction;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class PurgingMaw extends BaseCard {
    public static final String ID = makeID(PurgingMaw.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.ATTACK,
            AbstractCard.CardRarity.BASIC,
            CardTarget.ALL,
            3
    );

    private static final int DAMAGE = 5;
    private static final int HUNGER = 1;
    private static final int UPG_HUNGER = 1;

    public PurgingMaw() {
        super(ID, info);
        setDamage(DAMAGE);
        setMagic(HUNGER, UPG_HUNGER);
        cardsToPreview = new VoidCard();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int enemiesHit = 1;
        addToBot(new DamageAction(p, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        for(AbstractMonster e : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if(!e.isDead) {
                addToBot(new DamageAction(e, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                enemiesHit++;
            }
        }
        addToBot(new GainHungerAction(p, p, magicNumber * enemiesHit));
    }
}
