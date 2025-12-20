package spacemercs.cards.rare;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.SpawnModificationCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.cards.basic.BrokenOath;
import spacemercs.cards.basic.RememberedVow;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

import java.util.ArrayList;

public class Indecisive extends BaseCard implements SpawnModificationCard {
    public static final String ID = makeID(Indecisive.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.STATUS,
            CardRarity.RARE,
            CardTarget.NONE,
            -2
    );

    public Indecisive() {
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

    @Override
    public AbstractCard replaceWith(ArrayList<AbstractCard> currentRewardCards) {
        boolean hasVow = AbstractDungeon.player.masterDeck.findCardById(RememberedVow.ID) != null;
        boolean hasOath = AbstractDungeon.player.masterDeck.findCardById(BrokenOath.ID) != null;

        if(hasVow && !hasOath) {
            // give oath version
            return new StandFirm();
        } else if(!hasVow && hasOath) {
            // give vow version
            return new ChillingPast();
        } else {
            return new NewPath();
        }
    }
}
