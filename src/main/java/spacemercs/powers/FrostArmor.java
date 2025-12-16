package spacemercs.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static spacemercs.SpaceMercsMod.makeID;

public class FrostArmor extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(FrostArmor.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public FrostArmor(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        int blockToGain;
        if(isPlayer) {
            if(owner.hasPower(POWER_ID)) {
                blockToGain = getSlowStacks();
                if(blockToGain > 0) {
                    addToBot(new GainBlockAction(owner, blockToGain));
                }
            }
        }
    }

    private int getSlowStacks() {
        int retVal = 0;
        for (AbstractMonster e : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (e.hasPower(Slow.POWER_ID)) {
                retVal += e.getPower(Slow.POWER_ID).amount;
            }
        }

        return retVal;
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        addToBot(new RemoveSpecificPowerAction(owner, owner, this));

    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new FrostArmor(owner, amount);
    }
}
