package spacemercs.cards.rare;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.SpaceMercsCustomTags;
import spacemercs.cards.BaseCard;
import spacemercs.cards.actions.HotAndColdAction;
import spacemercs.cards.modifiers.WillAltCostModifier;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class HotAndCold extends BaseCard {
    public static final String ID = makeID(HotAndCold.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            3
    );

    public HotAndCold() {
        super(ID, info);
        setSelfRetain(true);
        setExhaust(true, false);
        tags.add(SpaceMercsCustomTags.APLLIESSLOW);
        CardModifierManager.addModifier(this, new WillAltCostModifier());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HotAndColdAction(m, p));
    }
}
