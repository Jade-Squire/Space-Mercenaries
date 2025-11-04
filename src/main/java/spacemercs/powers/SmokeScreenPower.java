package spacemercs.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

import static spacemercs.SpaceMercsMod.makeID;

public class SmokeScreenPower extends BasePower{
    public static final String POWER_ID = makeID(SmokeScreenPower.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = AbstractPower.PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public SmokeScreenPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster monster) {
        super.onPlayCard(card, monster);
        card.isCostModified = false;
        ArrayList<AbstractCard> hand = AbstractDungeon.player.hand.group;

        for (AbstractCard c : hand) {
            if(c.isCostModified) {
                c.costForTurn += 1;
                c.isCostModified = false;
            }
        }

        addToBot(new RemoveSpecificPowerAction(owner, owner, this));
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        super.onCardDraw(card);
        if(card.costForTurn > 0) {
            card.costForTurn -= 1;
            card.isCostModified = true;
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if(isPlayer) {
            ArrayList<AbstractCard> hand = AbstractDungeon.player.hand.group;
            for (AbstractCard c : hand) {
                if(c.isCostModified) {
                    c.costForTurn += 1;
                    c.isCostModified = false;
                }
            }
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
