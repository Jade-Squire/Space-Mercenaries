package spacemercs.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.SuppressPower;
import spacemercs.util.CardStats;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class DreadedVisage extends BaseCard {
    public static final String ID = makeID(DreadedVisage.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            3
    );

    private static final int BLOCK = 3;
    private static final int UPG_BLOCK = 1;
    private static final int SUPPRESS_STACKS = 1;
    private static final int UPG_SUPPRESS_STACKS = 1;

    public DreadedVisage() {
        super(ID, info);
        setBlock(BLOCK, UPG_BLOCK);
        setMagic(SUPPRESS_STACKS, UPG_SUPPRESS_STACKS);
        setSelfRetain(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int voids = 0;
        ArrayList<AbstractCard> cardsToExhaust = new ArrayList<>();

        for(AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if(c.cardID.equals(VoidCard.ID)) {
                voids++;
                cardsToExhaust.add(c);
            }
        }

        if(voids > 0) {
            for(int i = 0; i < voids; i++) {
                for (AbstractMonster e : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    addToBot(new ApplyPowerAction(e, p, new SuppressPower(e, magicNumber)));
                }
            }
        }

        for(AbstractCard c : cardsToExhaust) {
            addToBot(new ExhaustSpecificCardAction(c, AbstractDungeon.player.drawPile, true));
        }
    }
}
