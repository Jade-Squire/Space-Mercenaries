package spacemercs.cards.rare;

import basemod.abstracts.CustomSavable;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.SpawnModificationCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.cards.actions.GainHungerAction;
import spacemercs.cards.actions.UnwaveringStarVowAction;
import spacemercs.character.Cosmopaladin;
import spacemercs.interfaces.PermaScalingCard;
import spacemercs.util.CardStats;

import java.util.ArrayList;

@SuppressWarnings("unused")
@NoCompendium
@NoPools
public class UnwaveringStarVow extends BaseCard implements SpawnModificationCard, CustomSavable<Integer>, PermaScalingCard {
    public static final String ID = makeID(UnwaveringStarVow.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    private static final int BASE_HUNGER = 1;
    private static final int BASE_BLOCK = 1;
    private static final int INCREMENT_AMOUNT = 1;

    public UnwaveringStarVow() {
        super(ID, info);
        this.misc = BASE_HUNGER;
        this.block = BASE_BLOCK;
        this.baseBlock = this.block;
        this.baseMagicNumber = this.misc;
        this.magicNumber = this.baseMagicNumber;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new UnwaveringStarVowAction(this.uuid));
        addToBot(new GainHungerAction(p, p, this.baseMagicNumber));
        addToBot(new GainBlockAction(p, this.baseBlock));
    }

    @Override
    public boolean canSpawn(ArrayList<AbstractCard> currentRewardCards) { return false;}

    @Override
    public boolean canSpawnShop(ArrayList<AbstractCard> currentShopCards) {
        return false;
    }

    @Override
    public Integer onSave() {
        return this.baseBlock;
    }

    @Override
    public void onLoad(Integer blockAmt) {
        this.block = blockAmt;
        applyPowers();
    }

    @Override
    public void applyPowers() {
        this.baseMagicNumber = this.misc;
        this.baseBlock = this.block;
        super.applyPowers();
        this.initializeDescription();
    }

    @Override
    public void onLoadedMisc() {
        applyPowers();
    }

    @Override
    public AbstractCard makeCopy() {
        AbstractCard c = new UnwaveringStarVow();
        c.block = this.block;
        c.baseBlock = this.block;
        return c;
    }

    public void replaceSelf(AbstractCard newCard) {
        if(upgraded) {
            newCard.upgrade();
        }
        AbstractDungeon.player.masterDeck.addToTop(newCard);
        AbstractDungeon.player.masterDeck.removeCard(this);
    }

    public void replaceSelfMidCombat(CardGroup cardGroup, AbstractCard newCard) {
        if(upgraded) {
            newCard.upgrade();
        }
        int index = 0;
        for(AbstractCard c : cardGroup.group) {
            if(c == this) {
                break;
            }
            index++;
        }
        cardGroup.group.add(index, newCard);
        cardGroup.removeCard(this);
    }

    public void increaseScaling(boolean increaseBoth) {
        if(increaseBoth) {
            this.block += INCREMENT_AMOUNT;
            this.misc += INCREMENT_AMOUNT;
        } else {
            upgradeBlock = AbstractDungeon.cardRng.randomBoolean();
            if(upgradeBlock) {
                this.block += INCREMENT_AMOUNT;
            } else {
                this.misc += INCREMENT_AMOUNT;
            }
        }
        this.applyPowers();
    }

    @Override
    public void increaseScaling() {
        increaseScaling(true);
    }
}
