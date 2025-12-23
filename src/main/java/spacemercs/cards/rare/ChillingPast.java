package spacemercs.cards.rare;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.SpawnModificationCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.ChillingPastPower;
import spacemercs.util.CardStats;

import java.util.ArrayList;

@NoCompendium
public class ChillingPast extends BaseCard implements SpawnModificationCard {
    public static final String ID = makeID(ChillingPast.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    public ChillingPast() {
        super(ID, info);
        setInnate(false, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ChillingPastPower(p, -1), -1));
    }

    @Override
    public boolean canSpawn(ArrayList<AbstractCard> currentRewardCards) { return false;}

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
}
