package spacemercs.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import javassist.CtBehavior;
import spacemercs.interfaces.OnCreateCard;

@SuppressWarnings("unused")
public class TempCardAddedPatch {
    @SpirePatch2(clz = ShowCardAndAddToDrawPileEffect.class, method=SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCard.class, float.class, float.class, boolean.class, boolean.class, boolean.class})
    @SpirePatch2(clz = ShowCardAndAddToDrawPileEffect.class, method=SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCard.class, boolean.class, boolean.class})
    public static class EditTempCardBeforeAddedToDraw {
        @SpireInsertPatch(
                locator=Locator.class
        )
        public static void Insert(AbstractGameEffect __instance, AbstractCard ___card, AbstractCard srcCard) {
            AbstractDungeon.player.powers.stream().filter(power -> power instanceof OnCreateCard).forEach(power -> ((OnCreateCard)power).onCreateCardBeforeDrawPile(___card, srcCard));
        }

        @SpirePostfixPatch
        public static void Postfix(AbstractGameEffect __instance, AbstractCard ___card, AbstractCard srcCard) {
            AbstractDungeon.player.powers.stream().filter(power -> power instanceof OnCreateCard).forEach(power -> ((OnCreateCard)power).onCreateCardAfterDrawPile(___card, srcCard));
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(ShowCardAndAddToDrawPileEffect.class, "duration");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
