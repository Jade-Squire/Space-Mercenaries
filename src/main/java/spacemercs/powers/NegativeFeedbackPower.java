package spacemercs.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static spacemercs.SpaceMercsMod.makeID;

public class NegativeFeedbackPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(NegativeFeedbackPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    private static final int AMOUNT_PER_STACK = 6;

    public NegativeFeedbackPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(damageAmount > 0 && info.type != DamageInfo.DamageType.HP_LOSS && owner.hasPower(Amp.POWER_ID)) {
            flashWithoutSound();
            for(AbstractCreature e : AbstractDungeon.getCurrRoom().monsters.monsters) {
                addToBot(new ApplyPowerAction(e, owner, new Jolt(e, amount * AMOUNT_PER_STACK)));
            }
        }
        return super.onAttacked(info, damageAmount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + (amount * AMOUNT_PER_STACK) + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new NegativeFeedbackPower(owner, amount);
    }
}
