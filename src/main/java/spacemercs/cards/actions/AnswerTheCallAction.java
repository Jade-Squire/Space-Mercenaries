package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import spacemercs.relics.OfTheVoidRelic;

public class AnswerTheCallAction extends AbstractGameAction {
    private final float spawnX;
    private final float spawnY;
    public AnswerTheCallAction(AbstractCreature target, AbstractCreature owner, float cardX, float cardY) {
        this.setValues(target, owner);
        spawnX = cardX;
        spawnY = cardY;
    }

    @Override
    public void update() {
        AbstractDungeon.getCurrRoom().spawnRelicAndObtain(spawnX, spawnY, new OfTheVoidRelic());
        this.isDone = true;
    }
}
