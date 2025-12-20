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
import spacemercs.cards.actions.StandFirmAction;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.FrostArmor;
import spacemercs.util.CardStats;

import java.util.ArrayList;

@NoCompendium
public class StandFirm extends BaseCard implements SpawnModificationCard {
    public static final String ID = makeID(StandFirm.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    public StandFirm() {
        super(ID, info);
        setInnate(false, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new FrostArmor(p, -1), -1));
        addToBot(new StandFirmAction(p, p));
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
