package spacemercs.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import spacemercs.cards.actions.RefundHandlerAction;

import static spacemercs.SpaceMercsMod.makeID;

public class SturdyCircuitryPower extends BasePower {
    public static final String POWER_ID = makeID(SturdyCircuitryPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    private int refund1Cost = 0;
    private int refund2Cost = 0;

    public SturdyCircuitryPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 0);
        if(amount == 1) {
            refund1Cost += 1;
        } else {
            refund2Cost += 1;
        }
        updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        if(stackAmount == 1){
            refund1Cost += 1;
        } else {
            refund2Cost += 1;
        }
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        super.onUseCard(card, action);
        int cardCost = (card.cost == -1)? card.energyOnUse : card.costForTurn;
        int refundAmount = 0;

        if(cardCost > 2) {
            refundAmount += refund2Cost;
        }
        if(cardCost > 1) {
            refundAmount += refund1Cost;
        }
        if(refundAmount > 0) {
            flashWithoutSound();
            addToBot(new RefundHandlerAction(owner, owner, card, refundAmount));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
        if(refund1Cost > 0) {
            this.description += refund1Cost + DESCRIPTIONS[1] + "1.";
            if(refund2Cost > 0) {
                this.description += DESCRIPTIONS[2] + refund2Cost + DESCRIPTIONS[3] + "2.";
            }
        } else {
            this.description += refund2Cost + DESCRIPTIONS[1] + "2.";
        }
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        if (this.refund2Cost > 0 && this.refund1Cost <= 0) {
            if (!this.isTurnBased) {
                this.greenColor2.a = c.a;
                c = this.greenColor2;
            }

            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.refund2Cost), x, y, this.fontScale, c);
        } else if(this.refund1Cost > 0 && this.refund2Cost <= 0) {
            if (!this.isTurnBased) {
                this.greenColor2.a = c.a;
                c = this.greenColor2;
            }

            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.refund1Cost), x, y, this.fontScale, c);
        } else if(refund2Cost > 0) {
            if (!this.isTurnBased) {
                float alpha = c.a;
                c = this.greenColor2;
                c.a = alpha;
            }

            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.refund2Cost), x, y, this.fontScale, c);
            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.refund1Cost), x, y + 15.0F * Settings.scale, this.fontScale, c);
        }
    }
}
