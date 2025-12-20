package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RemoveRelicAction extends AbstractGameAction {
    private final String relicID;

    public RemoveRelicAction(AbstractCreature target, AbstractCreature owner, String relicID) {
        setValues(target, owner);
        this.relicID = relicID;
    }

    @Override
    public void update() {
        AbstractDungeon.player.loseRelic(relicID);
        this.isDone = true;
    }
}
