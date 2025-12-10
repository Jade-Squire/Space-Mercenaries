package spacemercs.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import spacemercs.interfaces.OnIgnition;

import static spacemercs.SpaceMercsMod.makeID;

public class RecrudescePower extends BasePower implements OnIgnition {
    public static final String POWER_ID = makeID(RecrudescePower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    private static final int JOLT_STACKS = 5;
    private static final int SCORCH_STACKS = 4;

    public RecrudescePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        amount2 = 0;
    }

    @Override
    public void stackPower(int stacks) {
        if(stacks == 1) {
            amount++;
        } else {
            amount2++;
        }
    }

    @Override
    public void onIgnite(AbstractCreature target) {
        if(amount > 0) {
            applyOne();
        }
        if(amount2 > 0) {
            applyAll();
        }
    }

    private void applyOne() {
        AbstractMonster target = AbstractDungeon.getRandomMonster();
        addToBot(new ApplyPowerAction(target, owner, new Jolt(target, JOLT_STACKS * amount)));
        addToBot(new ApplyPowerAction(target, owner, new Scorch(target, SCORCH_STACKS * amount)));
    }

    private void applyAll() {
        for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new ApplyPowerAction(m, owner, new Jolt(m, JOLT_STACKS * amount2)));
            addToBot(new ApplyPowerAction(m, owner, new Scorch(m, SCORCH_STACKS * amount2)));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
        if(amount > 0) {
            this.description += (amount * JOLT_STACKS) + DESCRIPTIONS[1] + (amount * SCORCH_STACKS) + DESCRIPTIONS[2];
            if(amount2 > 0) {
                this.description += DESCRIPTIONS[3] + (amount2 * JOLT_STACKS) + DESCRIPTIONS[1] + (amount2 * SCORCH_STACKS) + DESCRIPTIONS[4];
            }
        } else if(amount2 > 0) {
            this.description += (amount2 * JOLT_STACKS) + DESCRIPTIONS[1] + (amount2 * SCORCH_STACKS) + DESCRIPTIONS[4];
        }
    }

    @Override
    public void onInitialApplication() {
        if(amount == 2) {
            amount2 = 1;
            amount = 0;
        }
        updateDescription();
    }

    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        int tmp = amount;
        int tmp2 = amount2;
        amount += amount2;
        amount2 = 0;
        super.renderAmount(sb, x, y, c);
        amount = tmp;
        amount2 = tmp2;
    }
}
