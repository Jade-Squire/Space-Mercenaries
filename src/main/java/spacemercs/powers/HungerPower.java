package spacemercs.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import spacemercs.interfaces.OnCreateCard;

import static spacemercs.SpaceMercsMod.makeID;


/* * * * * * * * * * * * * * * * * * * * * * * * *
 *           amount = energy gain                 *
 *         amount2 = voids in deck                *
 * * * * * * * * * * * * * * * * * * * * * * * * */

public class HungerPower extends BasePower implements OnCreateCard, CloneablePowerInterface {
    public static final String POWER_ID = makeID(HungerPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public HungerPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void onCreateCardAfterDrawPile(AbstractCard card, AbstractCard src) {
        if(card != null && card.cardID.equals(VoidCard.ID)) {
            updateStacks();
        }
    }

    @Override
    public void onInitialApplication() {
        updateStacks();
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        updateStacks();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount2 + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    public void updateStacks() {
        int newAmount = 0;
        for(AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if(c.cardID.equals(VoidCard.ID)) {
                newAmount++;
            }
        }
        amount2 = newAmount;
        updateDescription();
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        super.renderAmount(sb, x, y, c);
        if (this.amount2 > 0) {
            if (!this.isTurnBased) {
                this.greenColor2.a = c.a;
                c = this.greenColor2;
            }

            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.amount2), x, y + 15.0F * Settings.scale, this.fontScale, c);
        } else if (this.amount2 < 0) {
            this.redColor2.a = c.a;
            c = this.redColor2;
            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.amount2), x, y + 15.0F * Settings.scale, this.fontScale, c);
        }

    }

    @Override
    public void atStartOfTurn() {
        addToBot(new GainEnergyAction(this.amount));
        this.amount = 0;
        updateDescription();
    }

    @Override
    public void onExhaust(AbstractCard card) {
        if (card.cardID.equals(VoidCard.ID)) {
            for(AbstractCard c : AbstractDungeon.player.drawPile.group) {
                if(c.uuid.equals(card.uuid)) {
                    this.amount2--;
                    break;
                }
            }
        }
        updateDescription();
    }

    @Override
    public AbstractPower makeCopy() {
        return new HungerPower(owner, amount);
    }
}
