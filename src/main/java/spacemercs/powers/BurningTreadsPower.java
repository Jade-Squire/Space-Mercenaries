package spacemercs.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static spacemercs.SpaceMercsMod.makeID;

public class BurningTreadsPower extends BasePower {
    public static final String POWER_ID = makeID(BurningTreadsPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;
    private static final int KINDLE_STACKS = 1;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public BurningTreadsPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void onPlayCard(AbstractCard card, AbstractMonster monster) {
        if(owner.hasPower(POWER_ID)) {
            addToBot(new ApplyPowerAction(owner, AbstractDungeon.player, new Kindle(owner, KINDLE_STACKS * amount)));
        }
    }

    public void atStartOfTurn() {
        if(owner.hasPower(POWER_ID)) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + (KINDLE_STACKS * amount) + DESCRIPTIONS[1];
    }
}
