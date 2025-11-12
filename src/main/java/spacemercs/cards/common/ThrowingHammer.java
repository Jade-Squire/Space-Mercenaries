package spacemercs.cards.common;

import basemod.ReflectionHacks;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.Cure;
import spacemercs.powers.Scorch;
import spacemercs.util.CardStats;

public class ThrowingHammer extends BaseCard {
    public static final String ID = makeID(ThrowingHammer.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            AbstractCard.CardType.ATTACK,
            AbstractCard.CardRarity.COMMON,
            AbstractCard.CardTarget.ENEMY,
            1
    );

    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int DAMAGE = 3;
    private static final int UPG_DAMAGE = 0;
    private static final int SCORCH_STACKS = 1;
    private static final int CURE_STACKS = 2;
    private static final int STRENGTH_STACKS = 1;

    public ThrowingHammer() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        if(upgraded) {
            addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, STRENGTH_STACKS)));
        }
        addToBot(new ApplyPowerAction(p, p, new Cure(p, CURE_STACKS)));
        addToBot(new ApplyPowerAction(m, p, new Scorch(m, SCORCH_STACKS)));
        if(!upgraded) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;

                    for (AbstractGameAction action : AbstractDungeon.actionManager.actions) {
                        if (action instanceof UseCardAction) {
                            if (ReflectionHacks.getPrivate(action, UseCardAction.class, "targetCard") == ThrowingHammer.this) {
                                ((UseCardAction) action).reboundCard = true;
                                break;
                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    public void upgrade(){
        super.upgrade();

        this.returnToHand = true;
    }


}
