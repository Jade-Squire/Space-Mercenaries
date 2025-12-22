package spacemercs.cards.common;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.SpaceMercsCustomTags;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.Slow;
import spacemercs.powers.WillPower;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class ColdStare extends BaseCard {
    public static final String ID = makeID(ColdStare.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            AbstractCard.CardRarity.COMMON,
            CardTarget.ALL_ENEMY,
            0
    );

    // hardcoded in card description
    private static final int SLOW = 2;
    private static final int WILL_GAIN = 1;
    private static final int UPG_WILL_GAIN = 1;

    public ColdStare() {
        super(ID, info);
        setMagic(WILL_GAIN, UPG_WILL_GAIN);
        tags.add(SpaceMercsCustomTags.APLLIESSLOW);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractMonster target = AbstractDungeon.getRandomMonster();
        addToBot(new ApplyPowerAction(target, p, new Slow(target, SLOW)));
        addToBot(new ApplyPowerAction(p, p, new WillPower(p, magicNumber)));
    }
}
