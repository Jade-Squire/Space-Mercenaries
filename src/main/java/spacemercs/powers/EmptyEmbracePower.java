package spacemercs.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import spacemercs.interfaces.OnCreateCard;

import static spacemercs.SpaceMercsMod.makeID;

public class EmptyEmbracePower extends BasePower implements OnCreateCard {
    public static final String POWER_ID = makeID(EmptyEmbracePower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public EmptyEmbracePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
        if(amount > 0) {
            this.description += DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public void onCreateCardBeforeDrawPile(AbstractCard c, AbstractCard src) {
        if(src.cardID.equals(VoidCard.ID)) {
            removeEthereal(src);
            removeEthereal(c);
        }
    }

    private void removeEthereal(AbstractCard c) {
        c.isEthereal = false;
        String[] split = c.rawDescription.split("Ethereal. NL ");
        if(split.length > 1) {
            c.rawDescription = split[0] + split[1];
            c.initializeDescription();
        }
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        for(AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if(c.cardID.equals(VoidCard.ID)) {
                removeEthereal(c);
            }
        }
        for(AbstractCard c : AbstractDungeon.player.hand.group) {
            if(c.cardID.equals(VoidCard.ID)) {
                removeEthereal(c);
            }
        }
        for(AbstractCard c : AbstractDungeon.player.discardPile.group) {
            if(c.cardID.equals(VoidCard.ID)) {
                removeEthereal(c);
            }
        }
        for(AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if(c.cardID.equals(VoidCard.ID)) {
                removeEthereal(c);
            }
        }
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        super.onCardDraw(card);
        if(card.cardID.equals(VoidCard.ID) && amount > 0) {
            flash();
            addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, amount, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
    }
}
