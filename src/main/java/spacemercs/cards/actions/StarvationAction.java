package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.UUID;

public class StarvationAction extends AbstractGameAction {
    private final int amount;
    private final UUID uuid;

    public StarvationAction(UUID targetUUID, int increaseAmt) {
        this.amount = increaseAmt;
        this.uuid = targetUUID;
    }

    @Override
    public void update() {
        for(AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if(c.uuid.equals(this.uuid)) {
                c.misc += this.amount;
                c.applyPowers();
            }
        }

        for(AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
            c.misc += this.amount;
            c.applyPowers();
        }

        this.isDone = true;
    }
}
