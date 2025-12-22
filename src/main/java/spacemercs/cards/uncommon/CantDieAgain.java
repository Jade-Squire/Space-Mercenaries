package spacemercs.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.cards.actions.CantDieHereAction;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.WillPower;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class CantDieAgain extends BaseCard {
    public static final String ID = makeID(CantDieAgain.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            3
    );

    private static final int WILL_GAIN = 4;

    public CantDieAgain() {
        super(ID, info);
        setMagic(WILL_GAIN);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new WillPower(p, magicNumber)));
        addToBot(new CantDieHereAction(p, p, upgraded));
    }

    @Override
    public boolean cardPlayable(AbstractMonster m) {
        if(!((float) AbstractDungeon.player.currentHealth / AbstractDungeon.player.maxHealth < 0.5F)){
            cantUseMessage = "I'm not below 50% health.";
            return false;
        }
        return super.cardPlayable(m);
    }
}
