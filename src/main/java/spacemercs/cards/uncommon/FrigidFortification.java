package spacemercs.cards.uncommon;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.SpaceMercsCustomTags;
import spacemercs.cards.BaseCard;
import spacemercs.cards.modifiers.WillAltCostModifier;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.FrigidFortificationPower;
import spacemercs.powers.FrostArmor;
import spacemercs.powers.Slow;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class FrigidFortification extends BaseCard {
    public static final String ID = makeID(FrigidFortification.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            3
    );

    private static final int SLOW = 1;
    private static final int UPG_SLOW = 2;

    public FrigidFortification() {
        super(ID, info);
        setMagic(SLOW, UPG_SLOW);
        setSelfRetain(true);
        CardModifierManager.addModifier(this, new WillAltCostModifier());
        tags.add(SpaceMercsCustomTags.APLLIESSLOW);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractMonster t : AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new ApplyPowerAction(t, p, new Slow(t, magicNumber)));
        }

        addToBot(new ApplyPowerAction(p, p, new FrostArmor(p, -1)));
        addToBot(new ApplyPowerAction(p, p, new FrigidFortificationPower(p, -1), -1));
    }
}
