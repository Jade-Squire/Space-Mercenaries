package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.UUID;

public class UnwaveringStarVowAction extends AbstractGameAction {
    private final int amount;
    private final UUID uuid;

    public UnwaveringStarVowAction(UUID targetUUID, int increaseAmt) {
        this.amount = increaseAmt;
        this.uuid = targetUUID;
    }

    @Override
    public void update() {
        boolean upgradeBlock = true;
        for(AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if(c.uuid.equals(this.uuid)) {
                if(c.upgraded) {
                    c.block += amount;
                    c.misc += amount;
                } else {
                    upgradeBlock = AbstractDungeon.cardRng.random() >= 0.5;
                    if(upgradeBlock) {
                        c.block += amount;
                    } else {
                        c.misc += amount;
                    }
                }
                c.applyPowers();
            }
        }

        for(AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
            if(c.upgraded) {
                c.block += amount;
                c.misc += amount;
            } else {
                if(upgradeBlock) {
                    c.block += amount;
                } else {
                    c.misc += amount;
                }
            }
            c.applyPowers();
        }
        this.isDone = true;
    }
}
