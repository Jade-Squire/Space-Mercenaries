package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BlurPower;

import java.util.ArrayList;

public class SatiationAction extends AbstractGameAction {
    private final int block;
    public SatiationAction(AbstractCreature target, AbstractCreature owner, int block) {
        this.setValues(target, owner);
        this.block = block;
    }

    @Override
    public void update() {
        ArrayList<AbstractCard> voids = new ArrayList<>();
        int amt = 0;
        for(AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if(c.cardID.equals(VoidCard.ID)) {
                voids.add(c);
                amt++;
            }
        }

        for(int i = 0; i < amt; i++) {
            addToBot(new GainBlockAction(target, block));
            addToBot(new ExhaustSpecificCardAction(voids.get(i), AbstractDungeon.player.drawPile, true));
        }

        addToBot(new ApplyPowerAction(target, source, new BlurPower(target, 1)));

        this.isDone = true;
    }
}
