package spacemercs.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.SpaceMercsCustomTags;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.Jolt;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class FlashGrenade extends BaseCard {
    public static final String ID = makeID(FlashGrenade.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            1
    );

    private static final int JOLT = 5;

    public FlashGrenade() {
        super(ID, info);
        setMagic(JOLT);
        tags.add(SpaceMercsCustomTags.GRENADE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractMonster monster1, monster2;
        monster1 = AbstractDungeon.getRandomMonster();
        if(monster1 == null) {
            return;
        }
        addToBot(new ApplyPowerAction(monster1, p, new Jolt(monster1, magicNumber)));
        if(upgraded && AbstractDungeon.getCurrRoom().monsters.monsters.size() > 1) {
            monster2 = AbstractDungeon.getRandomMonster(monster1);
            if(monster2 != null) {
                addToBot(new ApplyPowerAction(monster2, p, new Jolt(monster2, magicNumber)));
            }
        }
    }
}
