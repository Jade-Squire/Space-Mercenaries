package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class GainRelicAction extends AbstractGameAction {
    private final float spawnX;
    private final float spawnY;
    private final AbstractRelic relic;
    public GainRelicAction(AbstractCreature target, AbstractCreature owner, float cardX, float cardY, AbstractRelic relic) {
        this.setValues(target, owner);
        spawnX = cardX;
        spawnY = cardY;
        this.relic = relic;
    }

    @Override
    public void update() {
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(spawnX, spawnY, relic);
        this.isDone = true;
    }
}
