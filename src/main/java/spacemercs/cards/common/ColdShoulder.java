package spacemercs.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.Frozen;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class ColdShoulder extends BaseCard {
    public static final String ID = makeID(ColdShoulder.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    public ColdShoulder() {
        super(ID, info);
        setSelfRetain(false, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        boolean found = false;
        for(AbstractMonster t : AbstractDungeon.getCurrRoom().monsters.monsters) {
            for(AbstractPower pow : t.powers) {
                if(pow instanceof Frozen) {
                    addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, 1), 1));
                    found = true;
                    break;
                }
            }
            if(found) {
                break;
            }
        }
    }
}
