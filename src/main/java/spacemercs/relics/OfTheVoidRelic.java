package spacemercs.relics;

import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import spacemercs.cards.actions.GainHungerAction;
import spacemercs.character.Cosmopaladin;

import static spacemercs.SpaceMercsMod.makeID;

public class OfTheVoidRelic extends BaseRelic {
    private static final String NAME = OfTheVoidRelic.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SPECIAL; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    public OfTheVoidRelic() {
        super(ID, NAME, Cosmopaladin.Meta.CARD_COLOR, RARITY, SOUND);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onCardDraw(AbstractCard drawnCard) {
        super.onCardDraw(drawnCard);
        if(drawnCard.cardID.equals(VoidCard.ID)) {
            flash();
            addToTop(new GainHungerAction(AbstractDungeon.player, AbstractDungeon.player, 1, true));
            addToTop(new HealAction(AbstractDungeon.player, AbstractDungeon.player, 1));
        }
    }
}
