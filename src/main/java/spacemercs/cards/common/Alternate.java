package spacemercs.cards.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.cards.actions.AlternateAction;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class Alternate extends BaseCard {
    public static final String ID = makeID(Alternate.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.ATTACK,
            AbstractCard.CardRarity.COMMON,
            CardTarget.ALL_ENEMY,
            1
    );

    private static final int DAMAGE = 4;

    public Alternate() {
        super(ID, info);
        setDamage(DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DiscardAction(p, p, 1, false));
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                for(AbstractGameAction a : AbstractDungeon.actionManager.actions) {
                    if(a instanceof AlternateAction) {
                        ((AlternateAction)a).setDiscardedCard(AbstractDungeon.handCardSelectScreen.selectedCards.group.get(0));
                    }
                }
                AbstractDungeon.handCardSelectScreen.selectedCards.clear();
                this.isDone = true;
            }
        });
        addToBot(new AlternateAction(p, p, damage, upgraded));
    }
}
