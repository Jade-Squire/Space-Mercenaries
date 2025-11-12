package spacemercs.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.Scorch;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class Firewalker extends BaseCard {
    public static final String ID = makeID(Firewalker.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            2
    );

    private static final int SCORCH_STACKS = 1;
    private static final int UPG_KINDLE_STACKS = 2;
    private static final int ARTIFACT_STACKS = 1;

    public Firewalker() {
        super(ID, info);
        setMagic(SCORCH_STACKS, UPG_KINDLE_STACKS);
        setEthereal(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractMonster e : AbstractDungeon.getCurrRoom().monsters.monsters) {
            addToBot(new ApplyPowerAction(e, p, new Scorch(e, magicNumber)));
        }
        addToBot(new ApplyPowerAction(p, p, new ArtifactPower(p, ARTIFACT_STACKS)));
    }
}
