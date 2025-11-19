package spacemercs.cards.uncommon;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.cards.actions.GainHungerAction;
import spacemercs.cards.actions.SatiationAction;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class Satiation extends BaseCard {
    public static final String ID = makeID(Satiation.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            3
    );

    private static final int BLOCK = 3;
    private static final int HUNGER = 2;

    public Satiation() {
        super(ID, info);
        setBlock(BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(upgraded) {
            addToBot(new GainHungerAction(p, p, HUNGER, true));
        }
        addToBot(new SatiationAction(p, p, block));
    }
}
