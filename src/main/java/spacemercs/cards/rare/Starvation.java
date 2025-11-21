package spacemercs.cards.rare;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.SpawnModificationCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.StarvationPower;
import spacemercs.util.CardStats;

import java.util.ArrayList;

public class Starvation extends BaseCard implements SpawnModificationCard {
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
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new StarvationPower(p, BASE_HUNGER, this.magicNumber)));
    }

    @Override
    public boolean canSpawn(ArrayList<AbstractCard> currentCardRewards) {
        return false;
    }
}
