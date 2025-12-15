package spacemercs.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import spacemercs.cards.actions.CheckForIgnition;

import static spacemercs.SpaceMercsMod.makeID;

public class Scorch extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(Scorch.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;
    private int lastStack = -1;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public Scorch(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        if (AbstractDungeon.player.hasPower(PercussiveFlamesPower.POWER_ID)) {
            this.amount += AbstractDungeon.player.getPower(PercussiveFlamesPower.POWER_ID).amount;
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public void atStartOfTurn() {
        if(lastStack == owner.getPower(POWER_ID).amount) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, Scorch.POWER_ID));
        } else {
            lastStack = owner.getPower(POWER_ID).amount;
        }
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        addToBot(new CheckForIgnition(owner, owner));
    }

    public void onInitialApplication() {
        addToBot(new CheckForIgnition(owner, owner));
    }

    @Override
    public AbstractPower makeCopy() {
        return new Scorch(owner, amount);
    }
}
