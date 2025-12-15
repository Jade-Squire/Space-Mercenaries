package spacemercs.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnPlayerDeathPower;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import spacemercs.cards.rare.HowlingRevenge;

import static spacemercs.SpaceMercsMod.makeID;

public class HowlingRevengePower extends BasePower implements OnPlayerDeathPower, CloneablePowerInterface {
    public static final String POWER_ID = makeID(HowlingRevengePower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public int upgradeAmt = 0;

    public HowlingRevengePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
        if(upgradeAmt > 0) {
            this.description += Integer.toString(50);
        } else {
            this.description += Integer.toString(25);
        }
        this.description += DESCRIPTIONS[1];
    }

    @Override
    public void onInitialApplication() {
        updateDescription();
    }

    @Override
    public void reducePower(int reduceAmount) {
        super.reducePower(reduceAmount);
        if(upgradeAmt > 0) {
            upgradeAmt -= reduceAmount;
        }
    }

    @Override
    public boolean onPlayerDeath(AbstractPlayer abstractPlayer, DamageInfo damageInfo) {
        if(upgradeAmt > 0) {
            addToTop(new HealAction(abstractPlayer, abstractPlayer, abstractPlayer.maxHealth/2));
            removeCard(true);
        } else {
            addToTop(new HealAction(abstractPlayer, abstractPlayer, abstractPlayer.maxHealth/4));
            removeCard(false);
        }
        addToTop(new ReducePowerAction(abstractPlayer, abstractPlayer, this, 1));
        return false;
    }

    private void removeCard(boolean upgraded) {
        for(AbstractCard c: AbstractDungeon.player.masterDeck.group) {
            if(c instanceof HowlingRevenge) {
                if(upgraded == c.upgraded) {
                    AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    AbstractDungeon.player.masterDeck.removeCard(c);
                    return;
                }
            }
        }
        // didn't find version of card in deck with same upgrade value so just remove a different one
        // probably due to armaments style effect
        for(AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if(c instanceof HowlingRevenge) {
                AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(c, (float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                AbstractDungeon.player.masterDeck.removeCard(c);
                return;
            }
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new HowlingRevengePower(owner, amount);
    }
}
