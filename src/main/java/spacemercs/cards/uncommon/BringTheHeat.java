package spacemercs.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.Cure;
import spacemercs.util.CardStats;

public class BringTheHeat extends BaseCard {
    public static final String ID = makeID(BringTheHeat.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            2
    );

    public BringTheHeat() {
        super(ID, info);
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!p.hasPower(Cure.POWER_ID)) {
            return;
        }
        int effect = 1;
        if(upgraded) {
            effect++;
        }
        for(AbstractMonster e : AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new DamageAction(e, new DamageInfo(p, effect * p.getPower(Cure.POWER_ID).amount, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
        }
    }
}
