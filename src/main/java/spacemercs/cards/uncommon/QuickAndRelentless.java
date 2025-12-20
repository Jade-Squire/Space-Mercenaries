package spacemercs.cards.uncommon;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.SpaceMercsCustomTags;
import spacemercs.cards.BaseCard;
import spacemercs.cards.actions.QuickAndRelentlessAction;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class QuickAndRelentless extends BaseCard {
    public static final String ID = makeID(QuickAndRelentless.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    public QuickAndRelentless() {
        super(ID, info);
        tags.add(SpaceMercsCustomTags.APLLIESSLOW);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new QuickAndRelentlessAction(p, p, upgraded, 1));
    }
}
