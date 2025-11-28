package spacemercs.cards.basic;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.cards.actions.RememberedVowAction;
import spacemercs.cards.rare.AnswerTheCall;
import spacemercs.cards.rare.UnwaveringStarBase;
import spacemercs.cards.rare.UnwaveringStarOath;
import spacemercs.cards.rare.UnwaveringStarVow;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

import java.util.ArrayList;

/*  This one's a weird one.
    The misc value's sign is the cost reduction. then the next 15 bits are damage, skip 1, then last bits are block
 */
@SuppressWarnings("unused")
public class RememberedVow extends BaseCard implements OnObtainCard {
    public static final String ID = makeID(RememberedVow.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.BASIC,
            CardTarget.ENEMY,
            2
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
        setExhaust(true);
        if(shouldPreview) {
            this.cardsToPreview = new BrokenOath(false);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new RememberedVowAction(this.uuid, this.magicNumber));
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
                } else if(c.cardID.equals(UnwaveringStarBase.ID) || c.cardID.equals(UnwaveringStarOath.ID)) {
                    cardsToSwap.add(c);
                }
            }
            if(!cardsToSwap.isEmpty()) {
                for(AbstractCard c : cardsToSwap) {
                    if(foundOath) {
                        ((UnwaveringStarBase) c).replaceSelf(new UnwaveringStarVow());
                    } else {
                        ((UnwaveringStarOath) c).replaceSelf(new AnswerTheCall());
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
            } else if (c.cardID.equals(UnwaveringStarVow.ID) || c.cardID.equals(AnswerTheCall.ID)) {
                cardsToSwap.add(c);
            }
        }
        if(!cardsToSwap.isEmpty()) {
            for(AbstractCard c : cardsToSwap) {
                if(foundOath) {
                    ((UnwaveringStarVow) c).replaceSelf(new UnwaveringStarBase());
                } else {
                    ((AnswerTheCall) c).replaceSelf(new UnwaveringStarOath());
                }
            }
        }
    }
}
