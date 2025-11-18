package spacemercs.cards.rare;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.SpawnModificationCard;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.cards.actions.AnswerTheCallAction;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

import java.util.ArrayList;

public class AnswerTheCall extends BaseCard implements SpawnModificationCard {
    public static final String ID = makeID(AnswerTheCall.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.NONE,
            5
    );

    private static final int UPG_COST = 0;

    public AnswerTheCall() {
        super(ID, info);
        setCostUpgrade(UPG_COST);
        FleetingField.fleeting.set(this, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AnswerTheCallAction(p, p, this.current_x, this.current_y));
    }

    public void replaceSelf(AbstractCard newCard) {
        if(upgraded) {
            newCard.upgrade();
        }
        AbstractDungeon.player.masterDeck.addToTop(newCard);
        AbstractDungeon.player.masterDeck.removeCard(this);
    }

    @Override
    public boolean canSpawn(ArrayList<AbstractCard> currentRewardCards) { return false;}
}
