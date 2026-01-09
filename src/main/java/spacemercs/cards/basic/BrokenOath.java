package spacemercs.cards.basic;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import spacemercs.cards.BaseCard;
import spacemercs.cards.rare.*;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;
import spacemercs.util.TextureLoader;

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
        boolean foundVow = false;
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
                    foundVow = true;
                    if(c.misc >= 0) {
                        c.misc *= -1;
                        c.updateCost(-1);
                        ((RememberedVow)c).swapImage(true);
                    }
                } else if (c.cardID.equals(UnwaveringStarBase.ID) || c.cardID.equals(UnwaveringStarVow.ID) || c.cardID.equals(Indecisive.ID) || c.cardID.equals(ChillingPast.ID)) {
                    cardsToSwap.add(c);
                }
            }
            if(!cardsToSwap.isEmpty()) {
                for(AbstractCard c : cardsToSwap) {
                    if(foundVow) {
                        if(c instanceof UnwaveringStarBase) {
                            ((UnwaveringStarBase) c).replaceSelf(new UnwaveringStarOath());
                        } else {
                            ((Indecisive) c).replaceSelf(new StandFirm());
                        }
                    } else {
                        if(c instanceof UnwaveringStarVow) {
                            ((UnwaveringStarVow) c).replaceSelf((new AnswerTheCall()));
                        } else {
                            ((ChillingPast) c).replaceSelf(new NewPath());
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
        boolean foundVow = false;
        for(AbstractCard c : group.group) {
            if(c.cardID.equals(RememberedVow.ID)) {
                foundVow = true;
                if(c.misc >= 0) {
                    c.misc *= -1;
                    c.updateCost(-1);
                    ((RememberedVow)c).swapImage(true);
                }
            } else if (c.cardID.equals(UnwaveringStarBase.ID) || c.cardID.equals(UnwaveringStarVow.ID) || c.cardID.equals(Indecisive.ID) || c.cardID.equals(ChillingPast.ID)) {
                cardsToSwap.add(c);
            }
        }
        if(!cardsToSwap.isEmpty()) {
            for(AbstractCard c : cardsToSwap) {
                if(foundVow) {
                    if(c instanceof UnwaveringStarBase) {
                        ((UnwaveringStarBase) c).replaceSelfMidCombat(group, new UnwaveringStarOath());
                    } else {
                        ((Indecisive) c).replaceSelfMidCombat(group, new StandFirm());
                    }
                } else {
                    if(c instanceof UnwaveringStarVow) {
                        ((UnwaveringStarVow) c).replaceSelfMidCombat(group, new AnswerTheCall());
                    } else {
                        ((ChillingPast) c).replaceSelfMidCombat(group, new NewPath());
                    }
                }
            }
        }
    }

    @Override
    public void onLoadedMisc() {
        if(this.misc <= 0) {
            updateCost(-1);
            swapImage(true);
        }
    }

    @Override
    public void onObtainCard() {
        ArrayList<AbstractCard> cardsToSwap = new ArrayList<>();
        boolean foundVow = false;
        for(AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if(c.cardID.equals(RememberedVow.ID)) {
                foundVow = true;
                if(c.misc <= 0) {
                    c.misc *= -1;
                    c.updateCost(1);
                    c.isCostModified = false;
                    ((RememberedVow)c).swapImage(false);
                }
            } else if (c.cardID.equals(UnwaveringStarOath.ID) || c.cardID.equals(AnswerTheCall.ID) || c.cardID.equals(NewPath.ID) || c.cardID.equals(StandFirm.ID)) {
                cardsToSwap.add(c);
            }
        }
        if(!foundVow) {
            this.misc *= -1;
            this.updateCost(-1);
            this.isCostModified = true;
            this.swapImage(true);
        }
        if(!cardsToSwap.isEmpty()) {
            for(AbstractCard c : cardsToSwap) {
                if(foundVow) {
                    if(c instanceof UnwaveringStarOath) {
                        ((UnwaveringStarOath) c).replaceSelf(new UnwaveringStarBase());
                    } else {
                        ((StandFirm) c).replaceSelf(new Indecisive());
                    }
                } else {
                    if(c instanceof AnswerTheCall) {
                        ((AnswerTheCall) c).replaceSelf(new UnwaveringStarVow());
                    } else {
                        ((NewPath) c).replaceSelf(new ChillingPast());
                    }
                }
            }
        }
    }

    public void swapImage(boolean removed) {
        if(removed) {
            this.loadCardImage(TextureLoader.getCardTextureString("default_2", CardType.ATTACK));
        } else {
            this.loadCardImage(TextureLoader.getCardTextureString("default", CardType.ATTACK));
        }
    }
}
