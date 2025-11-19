package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class LoseMaxHPAction extends AbstractGameAction {
    public LoseMaxHPAction(AbstractPlayer target, AbstractCreature owner, int amount) {
        this.target = target;
        this.source = owner;
        this.amount = amount;
    }

    public void update() {
        AbstractDungeon.player.decreaseMaxHealth(amount);
        this.isDone = true;
    }
}
