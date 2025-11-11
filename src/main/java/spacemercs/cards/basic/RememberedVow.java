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
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

/*  This one's a weird one.
    The misc value is a 32-bit integer where the first 15 bits are the damage and the last 16 bits are the block. the sign bit is for if the cost should be upgraded
 */

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
        addToBot(new DamageAction(m, new DamageInfo(p, getDamage(), DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new GainBlockAction(p, getBlock()));
    }

    private int getBlock() {
        int retVal = Math.abs(this.misc);
        retVal = retVal << 16;
        retVal = retVal >> 16;
        return retVal;
    }

    private int getDamage() {
        int retVal = Math.abs(this.misc);
        retVal = retVal >> 16;
        return retVal;
    }

    public void onLoadedMisc() {
        if(this.misc <= 0) {
            updateCost(-1);
        }
        applyPowers();
    }

    public void applyPowers() {
        this.baseDamage = getDamage();
        this.baseBlock = getBlock();
        super.applyPowers();
        this.initializeDescription();
    }

    @Override
    public void onRemoveFromMasterDeck() {
        boolean shouldLowerCost = true;
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
                    if(c.misc >= 0) {
                        c.misc *= -1;
                        c.updateCost(-1);
                    }
                }
            }
        }
    }

    @Override
    public void onObtainCard() {
        for(AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if(c.cardID.equals(BrokenOath.ID)) {
                if(c.misc <= 0) {
                    c.misc *= -1;
                    c.updateCost(1);
                    c.isCostModified = false;
                }
            }
        }
    }
}
