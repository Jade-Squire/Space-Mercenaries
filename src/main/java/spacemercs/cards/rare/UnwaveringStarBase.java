package spacemercs.cards.rare;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.SpawnModificationCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.cards.basic.BrokenOath;
import spacemercs.cards.basic.RememberedVow;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class UnwaveringStarBase extends BaseCard implements SpawnModificationCard, OnObtainCard {
    public static final String ID = makeID(UnwaveringStarBase.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.STATUS,
            CardRarity.RARE,
            CardTarget.NONE,
            -2
    );

    public UnwaveringStarBase() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

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

    @Override
    public AbstractCard replaceWith(ArrayList<AbstractCard> currentRewardCards) {
        boolean hasVow = AbstractDungeon.player.masterDeck.findCardById(RememberedVow.ID) != null;
        boolean hasOath = AbstractDungeon.player.masterDeck.findCardById(BrokenOath.ID) != null;

        if(hasVow && hasOath) {
            return new UnwaveringStarBase();
        } else if(hasVow) {
            // give oath version
            return new UnwaveringStarOath();
        } else if(hasOath) {
            // give vow version
            return new UnwaveringStarVow();
        } else {
            return new AnswerTheCall();
        }
    }

    @Override
    public boolean canSpawn(ArrayList<AbstractCard> currentRewardCards) {
        if(AbstractDungeon.player instanceof Cosmopaladin) {
            return !((Cosmopaladin)AbstractDungeon.player).hasTakenStar();
        }
        return true;
    }

    @Override
    public boolean canSpawnShop(ArrayList<AbstractCard> currentShopCards) {
        if(AbstractDungeon.player instanceof Cosmopaladin) {
            return !((Cosmopaladin)AbstractDungeon.player).hasTakenStar();
        }
        return true;
    }


    @Override
    public void onObtainCard() {
        if(AbstractDungeon.player instanceof Cosmopaladin) {
            ((Cosmopaladin)AbstractDungeon.player).tookStar();
        }
    }
}