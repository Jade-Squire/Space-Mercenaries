package spacemercs.powers;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import static spacemercs.SpaceMercsMod.makeID;

public class Amp extends BasePower {
    public static final String POWER_ID = makeID(Amp.class.getSimpleName());
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public Amp(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        super.onPlayCard(card, m);
        flashWithoutSound();
        CardCrawlGame.sound.playA("ORB_LIGHTNING_EVOKE", 0.9F);
        CardCrawlGame.sound.playA("ORB_LIGHTNING_PASSIVE", -0.3F);
        AbstractMonster monster = AbstractDungeon.getRandomMonster();
        addToBot(new VFXAction(new LightningEffect(monster.hb.cX, monster.hb.cY)));
        addToBot(new DamageAction(monster, new DamageInfo(owner, amount)));
        addToBot(new ReducePowerAction(owner, owner, this, 1));
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        flashWithoutSound();
        if(damageAmount > 0 && info.type != DamageInfo.DamageType.HP_LOSS) {
            addToTop(new LoseHPAction(owner, owner, amount));
        }
        return super.onAttacked(info, damageAmount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }
}
