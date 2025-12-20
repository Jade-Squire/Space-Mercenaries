package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import spacemercs.cards.common.PhoenixRising;

import java.util.UUID;

public class PhoenixRisingAction extends AbstractGameAction {
    private final UUID uuid;

    public PhoenixRisingAction(UUID targetUUID) {
        this.uuid = targetUUID;
    }

    @Override
    public void update() {
        for(AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.uuid.equals(this.uuid)) {
                ((PhoenixRising)c).increaseScaling();
            }
        }

        for(AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
            ((PhoenixRising)c).increaseScaling();
        }

        this.isDone = true;
    }
}
