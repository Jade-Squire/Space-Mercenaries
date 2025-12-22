package spacemercs.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import spacemercs.cards.actions.GainHungerAction;

import static spacemercs.SpaceMercsMod.makeID;

public class StarvationPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(StarvationPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    //amount is the increment
    //amount2 is the current stack

    public StarvationPower(AbstractCreature owner, int amount, int increaseAmount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, increaseAmount);
        this.amount2 = amount;
        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount2 + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }

    @Override
    public void atStartOfTurn() {
        super.atStartOfTurn();
        flash();
        addToBot(new GainHungerAction(this.owner, this.owner, amount2, true));
        amount2 += amount;
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        if(isPlayer) {
            flash();
            //hopefully this doesnt count will
            int energy = EnergyPanel.getCurrentEnergy();
            for(int i = 0; i < energy; i++) {
                addToBot(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, 1, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            }
        }
    }

    @Override
    public void onInitialApplication() {
        updateDescription();
    }

    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        int tmp = amount;
        int tmp2 = amount2;
        amount = amount2;
        amount2 = 0;
        super.renderAmount(sb, x, y, c);
        amount2 = tmp2;
        amount = tmp;
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        this.amount2 += 1;
    }

    @Override
    public AbstractPower makeCopy() {
        return new StarvationPower(owner, amount2, amount);
    }
}
