package spacemercs.blockmods;

import com.evacipated.cardcrawl.mod.stslib.blockmods.AbstractBlockModifier;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;

import static spacemercs.SpaceMercsMod.makeID;

public class HeatSinkBlockMod extends AbstractBlockModifier {
    public static final String ID = makeID(HeatSinkBlockMod.class.getSimpleName());
    public final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public HeatSinkBlockMod() {
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
        return new HeatSinkBlockMod();
    }

    @Override
    public boolean isInherent() {
        return true;
    }

    @Override
    public Priority priority() {
        return Priority.BOTTOM;
    }
}
