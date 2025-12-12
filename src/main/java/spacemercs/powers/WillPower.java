package spacemercs.powers;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.patches.bothInterfaces.OnCreateCardInterface;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import spacemercs.cards.modifiers.WillCostModifier;

import java.util.ArrayList;

import static spacemercs.SpaceMercsMod.makeID;

public class WillPower extends BasePower implements OnCreateCardInterface {
    public static final String POWER_ID = makeID(WillPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public WillPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onInitialApplication() {
        for(AbstractCard c : AbstractDungeon.player.hand.group) {
            CardModifierManager.addModifier(c, new WillCostModifier());
        }
        for(AbstractCard c : AbstractDungeon.player.drawPile.group) {
            CardModifierManager.addModifier(c, new WillCostModifier());
        }
        for(AbstractCard c : AbstractDungeon.player.discardPile.group) {
            CardModifierManager.addModifier(c, new WillCostModifier());
        }
        for(AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            CardModifierManager.addModifier(c, new WillCostModifier());
        }
    }

    @Override
    public void onRemove() {
        if(AbstractDungeon.player.cardInUse != null) {
            ArrayList<AbstractCardModifier> mods = CardModifierManager.getModifiers(AbstractDungeon.player.cardInUse, WillCostModifier.ID);
            for(AbstractCardModifier mod : mods) {
                CardModifierManager.removeSpecificModifier(AbstractDungeon.player.cardInUse, mod, false);
            }
        }
        for(AbstractCard c : AbstractDungeon.player.hand.group) {
            ArrayList<AbstractCardModifier> mods = CardModifierManager.getModifiers(c, WillCostModifier.ID);
            for(AbstractCardModifier mod : mods) {
                CardModifierManager.removeSpecificModifier(c, mod, false);
            }
        }
        for(AbstractCard c : AbstractDungeon.player.drawPile.group) {
            ArrayList<AbstractCardModifier> mods = CardModifierManager.getModifiers(c, WillCostModifier.ID);
            for(AbstractCardModifier mod : mods) {
                CardModifierManager.removeSpecificModifier(c, mod, false);
            }
        }
        for(AbstractCard c : AbstractDungeon.player.discardPile.group) {
            ArrayList<AbstractCardModifier> mods = CardModifierManager.getModifiers(c, WillCostModifier.ID);
            for(AbstractCardModifier mod : mods) {
                CardModifierManager.removeSpecificModifier(c, mod, false);
            }
        }
        for(AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            ArrayList<AbstractCardModifier> mods = CardModifierManager.getModifiers(c, WillCostModifier.ID);
            for(AbstractCardModifier mod : mods) {
                CardModifierManager.removeSpecificModifier(c, mod, false);
            }
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onCreateCard(AbstractCard card) {
        CardModifierManager.addModifier(card, new WillCostModifier());
    }
}
