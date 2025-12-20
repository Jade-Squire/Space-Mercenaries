package spacemercs.cards.uncommon;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.SpaceMercsCustomTags;
import spacemercs.cards.BaseCard;
import spacemercs.cards.modifiers.WillAltCostModifier;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.Slow;
import spacemercs.powers.WillPower;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class HoarfrostStomp extends BaseCard {
    public static final String ID = makeID(HoarfrostStomp.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            -2
    );

    private static final int DAMAGE = 6;
    private static final int UPG_DAMAGE = 4;
    private static final int SLOW = 2;

    public HoarfrostStomp() {
        super(ID, info);
        this.returnToHand = true;
        setDamage(DAMAGE, UPG_DAMAGE);
        setMagic(SLOW);
        CardModifierManager.addModifier(this, new WillAltCostModifier());
        tags.add(SpaceMercsCustomTags.APLLIESSLOW);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Alt cost wouldn't be paid with base modifier so just remove 1 will
        if(p.hasPower(WillPower.POWER_ID)) {
            addToTop(new ReducePowerAction(p, p, WillPower.POWER_ID, 1));
        }

        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new ApplyPowerAction(m, p, new Slow(m, magicNumber)));
    }

    @Override
    public boolean hasEnoughEnergy() {
        if(!AbstractDungeon.player.hasPower(WillPower.POWER_ID)){
            cantUseMessage = "I don't have enough #rWill.";
            return false;
        }
        return super.hasEnoughEnergy();
    }
}
