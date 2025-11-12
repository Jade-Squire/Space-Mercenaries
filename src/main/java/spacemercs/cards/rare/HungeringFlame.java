package spacemercs.cards.rare;

import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.cards.actions.HungeringFlameAction;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class HungeringFlame extends BaseCard {
    public static final String ID = makeID(HungeringFlame.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    private static final int UPG_COST = 1;

    public HungeringFlame() {
        super(ID, info);
        setCostUpgrade(UPG_COST);
        setEthereal(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HungeringFlameAction(p, p));
        addToBot(new WaitAction(0.1F));
        addToBot(new PressEndTurnButtonAction());
    }
}
