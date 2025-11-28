package spacemercs.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.SpaceMercsCustomTags;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.PulseGrenadePower;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class PulseGrenade extends BaseCard {
    public static final String ID = makeID(PulseGrenade.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    private static final int JOLT = 3;
    private static final int UPG_JOLT = 2;

    public PulseGrenade() {
        super(ID, info);
        setMagic(JOLT, UPG_JOLT);
        tags.add(SpaceMercsCustomTags.GRENADE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new PulseGrenadePower(p, magicNumber)));
    }
}
