package spacemercs.cards.basic;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.cards.actions.RememberedVowAction;
import spacemercs.cards.rare.*;
import spacemercs.character.Cosmopaladin;
import spacemercs.interfaces.PermaScalingCard;
import spacemercs.util.CardStats;

import java.util.ArrayList;

/*  This one's a weird one.
    The misc value's sign is the cost reduction. then the next 15 bits are damage, skip 1, then last bits are block
 */
@SuppressWarnings("unused")
public class RememberedVow extends BaseCard implements OnObtainCard, PermaScalingCard {
    public static final String ID = makeID(RememberedVow.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.BASIC,
            CardTarget.ENEMY,
            1
    );

    private static final int BASE_DAMAGE = 1;
    private static final int BASE_BLOCK = 1;
    private static final int INCREASE_AMOUNT = 1;

    private static final int BLOCK_MASK = 32767;

    public RememberedVow() {
        this(true);
    }

    public RememberedVow(boolean shouldPreview) {
        super(ID, info);
        this.misc = (BASE_DAMAGE << 16) + BASE_BLOCK;
        this.baseMagicNumber = INCREASE_AMOUNT;
        this.magicNumber = baseMagicNumber;
        this.baseBlock = getBlock();
        this.baseDamage = getDamage();
        if(shouldPreview) {
            this.cardsToPreview = new BrokenOath(false);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new RememberedVowAction(this.uuid));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new GainBlockAction(p, block));
    }

    private int getBlock() {
        int retVal = Math.abs(this.misc);
        return retVal & BLOCK_MASK;
    }

    private int getDamage() {
        int retVal = Math.abs(this.misc);
        retVal = retVal >> 16;
        return retVal;
    }

    @Override
    public void onLoadedMisc() {
        if(this.misc <= 0) {
            updateCost(-1);
        }
        applyPowers();
    }

    @Override
    public void applyPowers() {
        this.baseDamage = getDamage();
        this.baseBlock = getBlock();
        super.applyPowers();
        this.initializeDescription();
    }

    @Override
    public void onRemoveFromMasterDeck() {
        boolean shouldLowerCost = true;
        boolean foundOath = false;
        ArrayList<AbstractCard> cardsToSwap = new ArrayList<>();
        // Check for other instances of Broken Oath
        for(AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.cardID.equals(ID)) {
                shouldLowerCost = false;
                break;
            }
        }

        if(shouldLowerCost) {
            for(AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                if(c.cardID.equals(BrokenOath.ID)) {
                    foundOath = true;
                    if(c.misc >= 0) {
                        c.misc *= -1;
                        c.updateCost(-1);
                    }
                } else if(c.cardID.equals(UnwaveringStarBase.ID) || c.cardID.equals(UnwaveringStarOath.ID) || c.cardID.equals(StandFirm.ID) || c.cardID.equals(Indecisive.ID)) {
                    cardsToSwap.add(c);
                }
            }
            if(!cardsToSwap.isEmpty()) {
                for(AbstractCard c : cardsToSwap) {
                    if(foundOath) {
                        if(c instanceof UnwaveringStarBase) {
                            ((UnwaveringStarBase) c).replaceSelf(new UnwaveringStarVow());
                        } else {
                            ((Indecisive) c).replaceSelf(new ChillingPast());
                        }
                    } else {
                        if(c instanceof UnwaveringStarOath) {
                            ((UnwaveringStarOath) c).replaceSelf(new AnswerTheCall());
                        } else {
                            ((StandFirm) c).replaceSelf(new NewPath());
                        }
                    }
                }
            }
            swapMidCombat(AbstractDungeon.player.hand);
            swapMidCombat(AbstractDungeon.player.drawPile);
            swapMidCombat(AbstractDungeon.player.discardPile);
            swapMidCombat(AbstractDungeon.player.exhaustPile);
        }
    }

    private void swapMidCombat(CardGroup group) {
        ArrayList<AbstractCard> cardsToSwap = new ArrayList<>();
        boolean foundOath = false;
        for(AbstractCard c : group.group) {
            if(c.cardID.equals(BrokenOath.ID)) {
                foundOath = true;
                if(c.misc >= 0) {
                    c.misc *= -1;
                    c.updateCost(-1);
                }
            } else if(c.cardID.equals(UnwaveringStarBase.ID) || c.cardID.equals(UnwaveringStarOath.ID) || c.cardID.equals(StandFirm.ID) || c.cardID.equals(Indecisive.ID)) {
                cardsToSwap.add(c);
            }
        }
        if(!cardsToSwap.isEmpty()) {
            for(AbstractCard c : cardsToSwap) {
                if(foundOath) {
                    if(c instanceof UnwaveringStarBase) {
                        ((UnwaveringStarBase) c).replaceSelfMidCombat(group, new UnwaveringStarVow());
                    } else {
                        ((Indecisive) c).replaceSelfMidCombat(group, new ChillingPast());
                    }
                } else {
                    if(c instanceof UnwaveringStarOath) {
                        ((UnwaveringStarOath) c).replaceSelfMidCombat(group, new AnswerTheCall());
                    } else {
                        ((StandFirm) c).replaceSelfMidCombat(group, new NewPath());
                    }
                }
            }
        }
    }

    @Override
    public void onObtainCard() {
        ArrayList<AbstractCard> cardsToSwap = new ArrayList<>();
        boolean foundOath = false;
        for(AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if(c.cardID.equals(BrokenOath.ID)) {
                foundOath = true;
                if(c.misc <= 0) {
                    c.misc *= -1;
                    c.updateCost(1);
                    c.isCostModified = false;
                }
            } else if (c.cardID.equals(UnwaveringStarVow.ID) || c.cardID.equals(AnswerTheCall.ID) || c.cardID.equals(NewPath.ID) || c.cardID.equals(ChillingPast.ID)) {
                cardsToSwap.add(c);
            }
        }
        if(!cardsToSwap.isEmpty()) {
            for(AbstractCard c : cardsToSwap) {
                if(foundOath) {
                    if(c instanceof UnwaveringStarVow) {
                        ((UnwaveringStarVow) c).replaceSelf(new UnwaveringStarBase());
                    } else {
                        ((ChillingPast) c).replaceSelf(new Indecisive());
                    }
                } else {
                    if(c instanceof AnswerTheCall) {
                        ((AnswerTheCall) c).replaceSelf(new UnwaveringStarOath());
                    } else {
                        ((NewPath) c).replaceSelf(new StandFirm());
                    }
                }
            }
        }
    }

    public void increaseScaling(boolean increaseBoth) {
        if(increaseBoth) {
            if (this.misc >= 0) {
                this.misc += magicNumber;
                this.misc += (magicNumber << 16);
            } else {
                this.misc -= magicNumber;
                this.misc -= (magicNumber << 16);
            }
        } else {
            boolean upgradeDamage = (AbstractDungeon.cardRng.randomBoolean());
            if(upgradeDamage) {
                if(this.misc >= 0) {
                    this.misc += (magicNumber << 16);
                } else {
                    this.misc -= (magicNumber << 16);
                }
            } else {
                if(this.misc >= 0) {
                    this.misc += magicNumber;
                } else {
                    this.misc -= magicNumber;
                }
            }
        }
        applyPowers();
    }

    @Override
    public void increaseScaling() {
        increaseScaling(true);
    }
}
