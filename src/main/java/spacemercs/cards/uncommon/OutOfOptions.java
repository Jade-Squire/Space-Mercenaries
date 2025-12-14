package spacemercs.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.Frozen;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class OutOfOptions extends BaseCard {
    public static final String ID = makeID(OutOfOptions.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            AbstractCard.CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            3
    );

    public OutOfOptions() {
        super(ID, info);
        setSelfRetain(false, true);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractMonster t: AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new ApplyPowerAction(t, p, new Frozen(t, 1)));
        }
        addToBot(new PressEndTurnButtonAction());
    }

    @Override
    public boolean cardPlayable(AbstractMonster m) {
        boolean retVal = AbstractDungeon.actionManager.cardsPlayedThisTurn.isEmpty();
        if(!retVal) {
            this.cantUseMessage = "Not the first card I played this turn.";
        }
        return retVal;
    }
}
