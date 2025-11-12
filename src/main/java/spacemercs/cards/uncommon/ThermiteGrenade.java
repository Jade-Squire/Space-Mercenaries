package spacemercs.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.SpaceMercsCustomTags;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.Thermite;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class ThermiteGrenade extends BaseCard {
    public static final String ID = makeID(ThermiteGrenade.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    //These will be used in the constructor. Technically you can just use the values directly,
    //but constants at the top of the file are easy to adjust.
    private static final int SCORCH_STACKS = 1;
    private static final int UPG_SCORCH_STACKS = 1;

    public ThermiteGrenade() {
        super(ID, info);
        setMagic(SCORCH_STACKS, UPG_SCORCH_STACKS);
        tags.add(SpaceMercsCustomTags.GRENADE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new Thermite(p, magicNumber), magicNumber));
    }
}
