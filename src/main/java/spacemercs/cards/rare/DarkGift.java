package spacemercs.cards.rare;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BottledFlame;
import com.megacrit.cardcrawl.relics.BottledLightning;
import com.megacrit.cardcrawl.relics.BottledTornado;
import spacemercs.cards.BaseCard;
import spacemercs.cards.actions.GainRelicAction;
import spacemercs.cards.actions.LoseMaxHPAction;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class DarkGift extends BaseCard {
    public static final String ID = makeID(DarkGift.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.NONE,
            6
    );

    private static final int HP_LOSS = 3;

    public DarkGift() {
        super(ID, info);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractRelic randomRelic;
        if(upgraded) {
            ArrayList<AbstractRelic> list = RelicLibrary.commonList;
            list.addAll(RelicLibrary.uncommonList);
            list.addAll(RelicLibrary.rareList);
            //list.addAll(RelicLibrary.bossList);
            list.removeIf(relic -> relic.relicId.equals(BottledFlame.ID) || relic.relicId.equals(BottledLightning.ID) || relic.relicId.equals(BottledTornado.ID));
            randomRelic = list.get(AbstractDungeon.cardRandomRng.random(list.size()-1));
        } else {
            randomRelic = RelicLibrary.commonList.get(AbstractDungeon.cardRandomRng.random(RelicLibrary.commonList.size() - 1));
        }
        addToBot(new LoseMaxHPAction(p, p, HP_LOSS));
        addToBot(new GainRelicAction(p, p, this.current_x, this.current_y, randomRelic.makeCopy()));
    }
}
