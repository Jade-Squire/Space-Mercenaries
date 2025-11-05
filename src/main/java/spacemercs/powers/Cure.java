package spacemercs.powers;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static spacemercs.SpaceMercsMod.makeID;

public class Cure extends BasePower{
    public static final String POWER_ID = makeID(Cure.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    private static boolean CAN_LOSE_STACKS = true;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public Cure(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(isPlayer){
            CAN_LOSE_STACKS = owner.currentHealth < owner.maxHealth;
        }
    }

    @Override
    public void wasHPLost(DamageInfo info, int damageAmount) {
        if(CAN_LOSE_STACKS) {
            addToTop(new ReducePowerAction(owner, owner, this, (int)Math.ceil(amount / 2.0)));
        }
    }

    @Override
    public void atStartOfTurn() {
        if(!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            addToBot(new HealAction(owner, owner, amount));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}