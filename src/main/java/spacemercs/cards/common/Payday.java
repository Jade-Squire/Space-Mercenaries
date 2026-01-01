package spacemercs.cards.common;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.DamageCallbackAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.cards.modifiers.GoldCostModifier;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class Payday extends BaseCard {
    public static final String ID = makeID(Payday.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.ATTACK,
            AbstractCard.CardRarity.COMMON,
            CardTarget.ENEMY,
            -2
    );

    private static final int DAMAGE = 10;
    private static final int GOLD = 15;
    private static final int UPG_GOLD = 5;
    private static final int COST = 15;

    public Payday() {
        super(ID, info);
        setDamage(DAMAGE);
        setMagic(GOLD, UPG_GOLD);
        CardModifierManager.addModifier(this, new GoldCostModifier(COST));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.player.loseGold(COST);

        addToBot(new DamageCallbackAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT, (callback) -> {
            if(m.isDying || m.isDead) {
                AbstractDungeon.player.gainGold(magicNumber);
            }
        }));
    }

    @Override
    public boolean hasEnoughEnergy() {
        if(AbstractDungeon.player.gold <= COST) {
            cantUseMessage = "I don't have enough #rGold.";
            return false;
        }
        return super.hasEnoughEnergy();
    }
}
