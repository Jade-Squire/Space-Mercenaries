package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class AlternateAction extends AbstractGameAction {
    private AbstractCard discardedCard = null;
    private final int damage;
    private final boolean upgraded;
    public AlternateAction(AbstractCreature target, AbstractCreature owner, int baseDamage, boolean upgraded) {
        this.setValues(target, owner);
        damage = baseDamage;
        this.upgraded = upgraded;
    }

    public void setDiscardedCard(AbstractCard card) {
        this.discardedCard = card;
    }

    @Override
    public void update() {
        if(this.discardedCard != null) {
            for(int i = 0; i < discardedCard.costForTurn; i++) {
                addToBot(new DamageRandomEnemyAction(new DamageInfo(source, damage), AttackEffect.SLASH_VERTICAL));
            }
            if(upgraded) {
                addToBot(new DrawCardAction(this.discardedCard.costForTurn));
            }
        }
        this.isDone = true;
    }
}
