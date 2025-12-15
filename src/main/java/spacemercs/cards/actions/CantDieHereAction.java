package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CantDieHereAction extends AbstractGameAction {
    private final boolean upgraded;
    public CantDieHereAction(AbstractCreature target, AbstractCreature owner, boolean upgraded) {
        this.setValues(target, owner);
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        int blockToGain = AbstractDungeon.player.maxHealth- AbstractDungeon.player.currentHealth;
        if(!upgraded) {
            blockToGain /= 2;
        }
        addToBot(new GainBlockAction(source, blockToGain));

        this.isDone = true;
    }
}
