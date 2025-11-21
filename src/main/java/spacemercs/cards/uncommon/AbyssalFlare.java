package spacemercs.cards.uncommon;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.cards.actions.AbyssalFlareAction;
import spacemercs.cards.actions.CheckForIgnition;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class AbyssalFlare extends BaseCard {
    public static final String ID = makeID(AbyssalFlare.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            3
    );

    public AbyssalFlare() {
        super(ID, info);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbyssalFlareAction(p, p));
        if(upgraded) {
            AbstractCreature target = AbstractDungeon.getRandomMonster();
            addToBot(new CheckForIgnition(target, p, true));
        }
    }
}
