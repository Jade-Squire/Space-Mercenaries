package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.powers.Slow;

public class GlacierGrenadeAction extends AbstractGameAction {
    private final int blockNeeded;
    private final int slowStacks;

    public GlacierGrenadeAction(AbstractCreature target, AbstractCreature owner, int blockNeeded, int slowStacks) {
        this.setValues(target, owner);
        this.blockNeeded = blockNeeded;
        this.slowStacks = slowStacks;
    }

    @Override
    public void update() {
        if(AbstractDungeon.player.currentBlock >= blockNeeded) {
            for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                addToBot(new ApplyPowerAction(m, source, new Slow(m, slowStacks)));
            }
        }
        this.isDone = true;
    }
}
