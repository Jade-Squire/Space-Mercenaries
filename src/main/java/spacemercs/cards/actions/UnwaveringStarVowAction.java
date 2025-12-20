package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import spacemercs.cards.rare.UnwaveringStarVow;

import java.util.UUID;

public class UnwaveringStarVowAction extends AbstractGameAction {
    private final UUID uuid;

    public UnwaveringStarVowAction(UUID targetUUID) {
        this.uuid = targetUUID;
    }

    @Override
    public void update() {
        for(AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if(c.uuid.equals(this.uuid)) {
                ((UnwaveringStarVow)c).increaseScaling(c.upgraded);
            }
        }

        for(AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
            ((UnwaveringStarVow)c).increaseScaling(c.upgraded);
        }
        this.isDone = true;
    }
}
