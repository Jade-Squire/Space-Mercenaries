package spacemercs.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import spacemercs.cards.actions.JoltAction;

import static spacemercs.SpaceMercsMod.makeID;

//Currently jolt damage can trigger jolt. idk if thats good

public class Jolt extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(Jolt.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public Jolt(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(info.output > 0 && AbstractDungeon.getCurrRoom().monsters.monsters.size() > 1) {
            addToTop(new JoltAction(AbstractDungeon.getRandomMonster((AbstractMonster) this.owner), this.owner, Math.min(info.output, amount)));
        }
        return super.onAttacked(info, damageAmount);
    }

    @Override
    public AbstractPower makeCopy() {
        return new Jolt(owner, amount);
    }
}
