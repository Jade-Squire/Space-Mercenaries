package spacemercs.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.Cure;
import spacemercs.powers.Scorch;
import spacemercs.util.CardStats;

public class FierySplendor extends BaseCard {
    public static final String ID = makeID(FierySplendor.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public FierySplendor() {
        super(ID, info);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p.hasPower(Cure.POWER_ID)) {
            addToBot(new ApplyPowerAction(p, p, new Cure(p, p.getPower(Cure.POWER_ID).amount)));
        }
        if(upgraded) {
            for (AbstractMonster e : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if(e.hasPower(Scorch.POWER_ID)) {
                    addToBot(new ApplyPowerAction(e, p, new Scorch(e, e.getPower(Scorch.POWER_ID).amount)));
                }
            }
        }
    }
}
