package spacemercs.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import spacemercs.interfaces.OnJolted;

import static spacemercs.SpaceMercsMod.makeID;

public class QuickAndRelentlessPower extends BasePower implements CloneablePowerInterface, OnJolted {
    public static final String POWER_ID = makeID(QuickAndRelentlessPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public QuickAndRelentlessPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
        if(amount2 > 0) {
            this.description += DESCRIPTIONS[1];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        QuickAndRelentlessPower p = new QuickAndRelentlessPower(owner, amount);
        p.amount2 = amount2;
        return p;
    }

    @Override
    public void OnJoltTriggered(AbstractCreature creature, int damage) {
        flashWithoutSound();
        addToTop(new ApplyPowerAction(creature, owner, new Slow(creature, damage), damage));
        if(amount2 > 0) {
            addToTop(new ApplyPowerAction(owner, owner, new Amp(owner, damage), damage));
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(isPlayer) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        int tmp = amount2;
        amount2 = 0;
        super.renderAmount(sb, x, y, c);
        amount2 = tmp;
    }
}
