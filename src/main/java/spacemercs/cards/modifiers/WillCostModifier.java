package spacemercs.cards.modifiers;

import basemod.abstracts.AbstractCardModifier;
import basemod.interfaces.AlternateCardCostModifier;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import spacemercs.powers.WillPower;

import java.awt.*;

import static spacemercs.SpaceMercsMod.makeID;

public class WillCostModifier extends AbstractCardModifier implements AlternateCardCostModifier{
    public boolean shouldSpend = false;
    public static final String ID = makeID(WillCostModifier.class.getSimpleName());
    private final Hitbox checkHb = new Hitbox(100.f * Settings.scale, 100.f * Settings.scale);

    public WillCostModifier() {}

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public AbstractCardModifier makeCopy() {
        WillCostModifier retVal = new WillCostModifier();
        retVal.shouldSpend = this.shouldSpend;
        return retVal;
    }

    @Override
    public int getAlternateResource(AbstractCard card) {
        if(!AbstractDungeon.player.hasPower(WillPower.POWER_ID) || !shouldSpend) {
            return -1;
        }
        return AbstractDungeon.player.getPower(WillPower.POWER_ID).amount / 2;
    }

    @Override
    public boolean prioritizeAlternateCost(AbstractCard card) {
        return false;
    }

    @Override
    public boolean canSplitCost(AbstractCard card) {
        return true;
    }

    @Override
    public int spendAlternateCost(AbstractCard abstractCard, int costToSpend) {
        int resource = -1;
        if(!shouldSpend) {
            return costToSpend;
        }
        if(AbstractDungeon.player.hasPower(WillPower.POWER_ID)) {
            resource = AbstractDungeon.player.getPower(WillPower.POWER_ID).amount / 2;
        }
        if(resource > costToSpend) {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, WillPower.POWER_ID, costToSpend * 2));
            costToSpend = 0;
        } else if(resource > 0) {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(AbstractDungeon.player, AbstractDungeon.player, WillPower.POWER_ID, resource * 2));
            costToSpend -= resource;
        }

        return costToSpend;
    }

    @Override
    public void onUpdate(AbstractCard card) {
        Vector2 energyLoc = new Vector2(-132.0F * card.drawScale * Settings.scale, 260.0f * card.drawScale * Settings.scale);
        energyLoc.rotate(card.angle);
        energyLoc.x += card.current_x;
        energyLoc.y += card.current_y;

        checkHb.resize(70.f * card.drawScale * Settings.scale, 70.f * card.drawScale * Settings.scale);
        checkHb.move(energyLoc.x, energyLoc.y);
        checkHb.update();

        if(InputHelper.justClickedRight) {
            if(AbstractDungeon.player.hoveredCard == card) {
                shouldSpend = !shouldSpend;
                card.applyPowers();
            }
        }
    }

    @Override
    public void onRender(AbstractCard card, SpriteBatch sb) {
        if(card.costForTurn == -1 && AbstractDungeon.player.hand.contains(card)) {
            sb.draw(ImageMaster.OPTION_TOGGLE, checkHb.cX - checkHb.width/2, checkHb.cY - checkHb.height/2, checkHb.width, checkHb.height);
            if(shouldSpend) {
                sb.setColor(Color.WHITE);
                sb.draw(ImageMaster.OPTION_TOGGLE_ON, checkHb.cX - checkHb.width/2, checkHb.cY - checkHb.height/2, checkHb.width, checkHb.height);
            }
        }
    }
}
