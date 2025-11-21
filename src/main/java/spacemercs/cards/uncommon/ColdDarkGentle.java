package spacemercs.cards.uncommon;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import spacemercs.cards.BaseCard;
import spacemercs.cards.actions.GainHungerAction;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.Frozen;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class ColdDarkGentle extends BaseCard {
    public static final String ID = makeID(ColdDarkGentle.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            10
    );

    private static final int DAMAGE = 40;
    private static final int UPG_DAMAGE = 20;
    private static final int HUNGER = 10;

    public ColdDarkGentle() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SMASH));
        addToBot(new GainHungerAction(p, p, HUNGER));
    }

    @Override
    public void triggerOnCardPlayed(AbstractCard cardPlayed) {
        checkCost();
    }

    @Override
    public void triggerWhenDrawn() {
        checkCost();
    }

    private boolean checkForFrozen (AbstractMonster exclude) {
        for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if(m != exclude) {
                for (AbstractPower p : m.powers) {
                    if (p.name.equals(Frozen.NAME)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void checkCost(AbstractMonster exclude) {
        boolean foundFrozen = checkForFrozen(exclude);
        if(!foundFrozen) {
            setCostForTurn(baseCost);
        } else {
            setCostForTurn(0);
        }
    }

    public void checkCost() {
        checkCost(null);
    }
}
