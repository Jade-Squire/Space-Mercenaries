package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class BroadsideAction extends AbstractGameAction {
    private final boolean upgraded;
    private final int damage;

    public BroadsideAction(boolean upgraded, int damage) {
        setValues(AbstractDungeon.player, AbstractDungeon.player);
        this.upgraded = upgraded;
        this.damage = damage;
    }

    @Override
    public void update() {
        int blockRemoved;
        if(upgraded) {
            blockRemoved = source.currentBlock / 2;
        } else {
            blockRemoved = source.currentBlock;
        }

        addToTop(new LoseBlockAction(AbstractDungeon.player, AbstractDungeon.player, blockRemoved));

        for(int i = 0; i < ((upgraded)? blockRemoved * 2 : blockRemoved); i++) {
            addToBot(new DamageRandomCombatantAction(new DamageInfo(source, damage), AttackEffect.SLASH_VERTICAL));
        }

        this.isDone = true;
    }
}
