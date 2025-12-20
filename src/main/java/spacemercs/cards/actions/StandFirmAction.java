package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMiscAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.GeneticAlgorithm;
import com.megacrit.cardcrawl.cards.colorless.RitualDagger;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import spacemercs.interfaces.PermaScalingCard;

public class StandFirmAction extends AbstractGameAction {
    public StandFirmAction(AbstractCreature target, AbstractCreature owner) {
        setValues(target, owner);
    }

    @Override
    public void update() {
        for(AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if(c instanceof PermaScalingCard) {
                ((PermaScalingCard)c).increaseScaling();
                for(AbstractCard card : GetAllInBattleInstances.get(c.uuid)) {
                    ((PermaScalingCard)card).increaseScaling();
                }
            } else if (c instanceof GeneticAlgorithm) {
                addToBot(new IncreaseMiscAction(c.uuid, c.misc, c.magicNumber));
            } else if (c instanceof RitualDagger) {
                for(AbstractCard card : AbstractDungeon.player.masterDeck.group) {
                    if (card.uuid.equals(c.uuid)) {
                        card.misc += card.magicNumber;
                        card.applyPowers();
                        card.baseDamage = c.misc;
                        card.isDamageModified = false;
                    }
                }
                for(AbstractCard card : GetAllInBattleInstances.get(c.uuid)) {
                    card.misc += card.magicNumber;
                    card.applyPowers();
                    card.baseDamage = card.misc;
                }
            }
        }
        this.isDone = true;
    }
}
