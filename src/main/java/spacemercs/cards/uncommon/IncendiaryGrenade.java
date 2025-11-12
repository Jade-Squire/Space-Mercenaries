package spacemercs.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.SpaceMercsCustomTags;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.Cure;
import spacemercs.powers.Scorch;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class IncendiaryGrenade extends BaseCard {
    public static final String ID = makeID(IncendiaryGrenade.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            AbstractCard.CardTarget.ENEMY,
            1
    );

    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int SCORCH_STACKS = 3;
    private static final int UPG_SCORCH_STACKS = 2;
    private static final int CURE_STACKS = 3;

    public IncendiaryGrenade() {
        super(ID, info);
        setMagic(SCORCH_STACKS, UPG_SCORCH_STACKS);
        tags.add(SpaceMercsCustomTags.GRENADE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new Scorch(m, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new Cure(p, CURE_STACKS), CURE_STACKS));
    }
}
