package spacemercs.cards.basic;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import spacemercs.cards.BaseCard;
import spacemercs.cards.rare.UnwaveringStarBase;
import spacemercs.cards.rare.UnwaveringStarOath;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class BrokenOath extends BaseCard  implements OnObtainCard {
    public static final String ID = makeID(BrokenOath.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.BASIC,
            CardTarget.ENEMY,
            3
    );

    private static final int BASE_DAMAGE = 10;
    private static final int UPG_DAMAGE = 5;
    private static final int BASE_VULN = 2;
    private static final int UPG_VULN = 1;

    public BrokenOath() {
        this(true);
    }

    public BrokenOath(boolean shouldPreview) {
        super(ID, info);
        setDamage(BASE_DAMAGE, UPG_DAMAGE);
        setMagic(BASE_VULN, UPG_VULN);
        if(shouldPreview) {
            this.cardsToPreview = new RememberedVow(false);
        }
        this.misc = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, magicNumber, false)));
    }

    @Override
    public void onRemoveFromMasterDeck() {
        boolean shouldLowerCost = true;
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
                if(c.cardID.equals(RememberedVow.ID)) {
                    if(c.misc >= 0) {
                        c.misc *= -1;
                        c.updateCost(-1);
                    }
                } else if (c.cardID.equals(UnwaveringStarBase.ID)) {
                    cardsToSwap.add(c);
                }
            }
            if(!cardsToSwap.isEmpty()) {
                for(AbstractCard c : cardsToSwap) {
                    ((UnwaveringStarBase) c).replaceSelf(new UnwaveringStarOath());
                }
            }
        }
    }

    @Override
    public void onLoadedMisc() {
        if(this.misc <= 0) {
            updateCost(-1);
        }
    }

    @Override
    public void onObtainCard() {
        ArrayList<AbstractCard> cardsToSwap = new ArrayList<>();
        for(AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if(c.cardID.equals(RememberedVow.ID)) {
                if(c.misc <= 0) {
                    c.misc *= -1;
                    c.updateCost(1);
                    c.isCostModified = false;
                }
            } else if (c.cardID.equals(UnwaveringStarOath.ID)) {
                cardsToSwap.add(c);
            }
        }
        if(!cardsToSwap.isEmpty()) {
            for(AbstractCard c : cardsToSwap) {
                ((UnwaveringStarOath)c).replaceSelf(new UnwaveringStarBase());
            }
        }
    }
}
