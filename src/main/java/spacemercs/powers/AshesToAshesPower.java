package spacemercs.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import spacemercs.SpaceMercsCustomTags;

import static spacemercs.SpaceMercsMod.makeID;

public class AshesToAshesPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(AshesToAshesPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public AshesToAshesPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void onAfterCardPlayed(AbstractCard usedCard) {
        if(usedCard.hasTag(SpaceMercsCustomTags.GRENADE)) {
            usedCard.returnToHand = true;
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new AshesToAshesPower(owner, amount);
    }
}
