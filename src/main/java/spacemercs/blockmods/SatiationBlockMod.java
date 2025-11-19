package spacemercs.blockmods;

import com.evacipated.cardcrawl.mod.stslib.blockmods.AbstractBlockModifier;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import static spacemercs.SpaceMercsMod.makeID;

public class SatiationBlockMod extends AbstractBlockModifier {
    public static final String ID = makeID(SatiationBlockMod.class.getSimpleName());
    public final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public SatiationBlockMod() {
        super();
    }

    @Override
    public int amountLostAtStartOfTurn() {
        return 0;
    }

    @Override
    public String getName() {
        return cardStrings.NAME;
    }

    @Override
    public String getDescription() {
        return cardStrings.DESCRIPTION;
    }

    @Override
    public AbstractBlockModifier makeCopy() {
        return new SatiationBlockMod();
    }

    @Override
    public boolean isInherent() {
        return true;
    }
}
