package spacemercs.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.SpaceMercsCustomTags;
import spacemercs.cards.BaseCard;
import spacemercs.cards.actions.GlacierGrenadeAction;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class GlacierGrenade extends BaseCard {
    public static final String ID = makeID(GlacierGrenade.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int BLOCK = 6;
    private static final int BLOCK_NEEDED = 20;
    private static final int UPG_BLOCK_NEEDED = -5;
    // hardcoded in description, change in both places
    private static final int SLOW_STACKS = 3;

    public GlacierGrenade() {
        super(ID, info);
        setBlock(BLOCK);
        setMagic(BLOCK_NEEDED, UPG_BLOCK_NEEDED);
        tags.add(SpaceMercsCustomTags.GRENADE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new GlacierGrenadeAction(p, p, magicNumber, SLOW_STACKS));
    }
}
