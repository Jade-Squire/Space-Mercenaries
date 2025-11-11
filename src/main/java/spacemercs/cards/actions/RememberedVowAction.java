package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;

import java.util.UUID;

public class RememberedVowAction extends AbstractGameAction {
    private final int amount;
    private final UUID uuid;

    public RememberedVowAction(UUID targetUUID, int increaseAmt) {
        this.amount = increaseAmt;
        this.uuid = targetUUID;
    }

    public void update() {
        boolean upgradeDamage = true;
        for(AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if(c.uuid.equals(this.uuid)) {
                if(c.upgraded) {
                    if(c.misc >= 0) {
                        c.misc += this.amount;
                        c.misc += (this.amount << 16);
                    } else {
                        c.misc -= this.amount;
                        c.misc -= (this.amount << 16);
                    }
                } else {
                    upgradeDamage = (Math.random() >= 0.5);
                    if(upgradeDamage) {
                        if(c.misc >= 0) {
                            c.misc += (amount << 16);
                        } else {
                            c.misc -= (amount << 16);
                        }
                    } else {
                        if(c.misc >= 0) {
                            c.misc += amount;
                        } else {
                            c.misc -= amount;
                        }

                    }
                }
                c.applyPowers();
            }
        }

        for(AbstractCard c : GetAllInBattleInstances.get(this.uuid)) {
            if(c.upgraded) {
                if(c.misc >= 0) {
                    c.misc += this.amount;
                    c.misc += (this.amount << 16);
                } else {
                    c.misc -= this.amount;
                    c.misc -= (this.amount << 16);
                }
            } else {
                if(upgradeDamage) {
                    if(c.misc >= 0) {
                        c.misc += (amount << 16);
                    } else {
                        c.misc -= (amount << 16);
                    }
                } else {
                    if(c.misc >= 0) {
                        c.misc += amount;
                    } else {
                        c.misc -= amount;
                    }

                }
            }
            c.applyPowers();
        }

        this.isDone = true;
    }
}
