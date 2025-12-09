package spacemercs.cards.actions;

import com.evacipated.cardcrawl.mod.stslib.actions.common.RefundAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.RefundFields;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RefundHandlerAction extends AbstractGameAction {
    private int refundAmt;
    private final AbstractCard targetCard;

    public RefundHandlerAction(AbstractCreature target, AbstractCreature owner, AbstractCard targetCard, int amount) {
        this.setValues(target, owner);
        refundAmt = amount;
        this.targetCard = targetCard;
    }

    @Override
    public void update() {
        for(AbstractGameAction action : AbstractDungeon.actionManager.actions) {
            if(action instanceof RefundHandlerAction) {
                ((RefundHandlerAction)action).addRefund(refundAmt);
                this.isDone = true;
                return;
            }
        }
        if(RefundFields.refund.get(targetCard) > 0) {
            refundAmt -= RefundFields.refund.get(targetCard);
        }

        addToBot(new RefundAction(targetCard, refundAmt, targetCard.energyOnUse));

        this.isDone = true;
    }

    public void addRefund(int amount) {
        refundAmt += amount;
        if(refundAmt > targetCard.energyOnUse) {
            refundAmt = targetCard.energyOnUse;
        }
    }
}