package spacemercs.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnLoseBlockPower;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.OnMyBlockBrokenPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static spacemercs.SpaceMercsMod.makeID;

public class GlacialGuardPower extends BasePower implements OnLoseBlockPower, OnMyBlockBrokenPower, CloneablePowerInterface {
    public static final String POWER_ID = makeID(GlacialGuardPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public boolean shouldOnBreak = false;
    private static final int SLOW_ON_BREAK = 5;

    public GlacialGuardPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        if(shouldOnBreak) {
            this.description += DESCRIPTIONS[2] + amount2 * SLOW_ON_BREAK + DESCRIPTIONS[3];
        }
    }

    @Override
    public int onLoseBlock(DamageInfo damageInfo, int i) {
        if(damageInfo.owner != owner) {
            addToBot(new ApplyPowerAction(damageInfo.owner, owner, new Slow(damageInfo.owner, amount)));
        }
        return i;
    }

    @Override
    public void atStartOfTurn() {
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void onInitialApplication() {
        updateDescription();
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        this.amount2 *= SLOW_ON_BREAK;
        super.renderAmount(sb, x, y, c);
        this.amount2 /= SLOW_ON_BREAK;
    }

    @Override
    public void onMyBlockBroken() {
        if(shouldOnBreak) {
            for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                addToBot(new ApplyPowerAction(m, owner, new Slow(m, amount2 * SLOW_ON_BREAK)));
            }
        }
    }

    @Override
    public AbstractPower makeCopy() {
        GlacialGuardPower p = new GlacialGuardPower(owner, amount);
        p.shouldOnBreak = this.shouldOnBreak;
        return p;
    }
}
