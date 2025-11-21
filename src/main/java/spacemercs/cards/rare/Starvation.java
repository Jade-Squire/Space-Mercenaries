package spacemercs.cards.rare;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.cards.actions.StarvationAction;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.StarvationPower;
import spacemercs.util.CardStats;

public class Starvation extends BaseCard {
    public static final String ID = makeID(Starvation.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    private static final int BASE_HUNGER = 1;
    private static final int BASE_HUNGER_INC = 1;
    private static final int UPG_HUNGER_INC = 1;

    public Starvation() {
        super(ID, info);
        setMagic(BASE_HUNGER_INC, UPG_HUNGER_INC);
        this.misc = BASE_HUNGER;
        this.baseDamage = this.misc;
    }

    @Override
    public void applyPowers() {
        this.baseDamage = this.misc;
        super.applyPowers();
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new StarvationAction(this.uuid, this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new StarvationPower(p, this.misc)));
    }

    @Override
    public void onLoadedMisc() {
        applyPowers();
    }
}
