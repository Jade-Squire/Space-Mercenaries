package spacemercs.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import spacemercs.interfaces.OnPreDiscard;

@SuppressWarnings("unused")
public class BeforeDiscardHookPatch {
    @SpirePatch2(clz = CardGroup.class, method="moveToDiscardPile")
    public static class AddHookBeforeDiscard {
        public static SpireReturn<Void> Prefix(AbstractCard c) {
            SpireReturn<Void> retVal = SpireReturn.Continue();
            for(AbstractPower p : AbstractDungeon.player.powers) {
                if(p instanceof OnPreDiscard) {
                    if(((OnPreDiscard) p).onDiscard(c)) {
                        retVal = SpireReturn.Return();
                    }
                }
            }
            return retVal;
        }
    }
}
