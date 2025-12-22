package spacemercs.cards.rare;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.cards.modifiers.WillAltCostModifier;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.EmptyAndUnmovingPower;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class EmptyAndUnmoving extends BaseCard {
    public static final String ID = makeID(EmptyAndUnmoving.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.RARE,
            CardTarget.SELF,
            4
    );

    public EmptyAndUnmoving() {
        super(ID, info);
        setInnate(false, true);
        CardModifierManager.addModifier(this, new WillAltCostModifier());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new EmptyAndUnmovingPower(p, -1)));
    }
}
