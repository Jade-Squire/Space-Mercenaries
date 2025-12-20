package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import spacemercs.cards.basic.RememberedVow;

import java.util.UUID;

public class RememberedVowAction extends AbstractGameAction {
    private final UUID uuid;

    public RememberedVowAction(UUID targetUUID) {
        this.uuid = targetUUID;
    }

    @Override
    public void update() {
        for(AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if(c.uuid.equals(this.uuid)) {
                ((RememberedVow)c).increaseScaling(c.upgraded);
            }
        }

        for(AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
            ((RememberedVow)c).increaseScaling(c.upgraded);
        }

        this.isDone = true;
    }
}
