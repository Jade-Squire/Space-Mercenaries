package spacemercs.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import spacemercs.cards.actions.GainHungerAction;

import static spacemercs.SpaceMercsMod.makeID;

public class VengeancePowerEnemy extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(VengeancePowerEnemy.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    public VengeancePowerEnemy(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onDeath() {
        addToBot(new GainHungerAction(AbstractDungeon.player, AbstractDungeon.player, this.owner.maxHealth));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new VengeancePowerEnemy(owner, amount);
    }
}
