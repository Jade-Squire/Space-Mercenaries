package spacemercs.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.SpaceMercsCustomTags;
import spacemercs.cards.BaseCard;
import spacemercs.cards.actions.CheckForEruption;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.Cure;
import spacemercs.powers.Kindle;
import spacemercs.util.CardStats;

public class IncendiaryGrenade extends BaseCard {
    public static final String ID = makeID(IncendiaryGrenade.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            AbstractCard.CardTarget.ENEMY,
            0
    );

    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int KINDLE_STACKS = 2;
    private static final int CURE_STACKS = 0;
    private static final int UPG_CURE_STACKS = 2;

    public IncendiaryGrenade() {
        super(ID, info);
        setMagic(CURE_STACKS, UPG_CURE_STACKS);
        tags.add(SpaceMercsCustomTags.GRENADE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new Kindle(m, KINDLE_STACKS)));
        addToBot(new CheckForEruption(m, p));
        if(upgraded) {
            addToBot(new ApplyPowerAction(p, p, new Cure(p, magicNumber), magicNumber));
        }
    }
}
