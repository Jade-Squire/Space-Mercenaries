package spacemercs.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.SpaceMercsCustomTags;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.VoidwallPower;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class VoidwallGrenade extends BaseCard {
    public static final String ID = makeID(VoidwallGrenade.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            3
    );

    private static final int SUPPRESS_STACKS = 1;
    private static final int UPG_SUPPRESS_STACKS = 1;

    public VoidwallGrenade() {
        super(ID, info);
        setMagic(SUPPRESS_STACKS, UPG_SUPPRESS_STACKS);
        tags.add(SpaceMercsCustomTags.GRENADE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new VoidwallPower(p, magicNumber)));
    }
}
