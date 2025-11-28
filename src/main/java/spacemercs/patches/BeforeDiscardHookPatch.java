package spacemercs.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.defect.ScrapeFollowUpAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;
import spacemercs.interfaces.OnPreDiscard;

@SuppressWarnings("unused")
public class BeforeDiscardHookPatch {
    @SpirePatch2(clz = DiscardAction.class, method="update")
    @SpirePatch2(clz = DiscardSpecificCardAction.class, method="update")
    public static class AddHookBeforeDiscard {
        public static SpireReturn<Void> Prefix(AbstractGameAction __instance) {
            SpireReturn<Void> retVal = SpireReturn.Continue();
            for(AbstractPower p : AbstractDungeon.player.powers) {
                if(p instanceof OnPreDiscard) {
                    if(((OnPreDiscard) p).onDiscard((__instance instanceof DiscardSpecificCardAction)? 1 : __instance.amount)) {
                        __instance.isDone = true;
                        retVal = SpireReturn.Return();
                    }
                }
            }
            return retVal;
        }
    }

    @SpirePatch2(clz = ScrapeFollowUpAction.class, method="update")
    public static class AddHookBeforeScrapeDiscard {
        @SpireInsertPatch(
                locator=Locator.class
        )
        public static SpireReturn<Void> Insert(AbstractGameAction __instance) {
            SpireReturn<Void> retVal = SpireReturn.Continue();
            if(__instance.isDone) {
                for(AbstractCard c : DrawCardAction.drawnCards) {
                    if (c.costForTurn != 0 && !c.freeToPlayOnce) {
                        for(AbstractPower p : AbstractDungeon.player.powers) {
                            if(p instanceof OnPreDiscard) {
                                if(((OnPreDiscard) p).onDiscard(1)) {
                                    retVal = SpireReturn.Return();
                                }
                            }
                        }
                    }
                }
            }
            return retVal;
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(ScrapeFollowUpAction.class, "isDone");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
