package spacemercs.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.interfaces.OnMonsterDeath;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class HonorTheFallen extends BaseCard implements OnMonsterDeath {
    public static final String ID = makeID(HonorTheFallen.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            2
    );

    private static final int BLOCK = 1;

    public HonorTheFallen() {
        super(ID, info);
        this.misc = BLOCK;
        this.baseBlock = this.misc;
        setSelfRetain(false, true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
    }

    @Override
    public void onMonsterDeath(AbstractMonster m) {
        this.misc++;
        this.baseBlock++;
        applyPowers();
        initializeDescription();
    }

    @Override
    public void onLoadedMisc() {
        this.baseBlock = this.misc;
        applyPowers();
        initializeDescription();
    }
}
