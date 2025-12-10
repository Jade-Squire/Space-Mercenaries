package spacemercs.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import spacemercs.character.Cosmopaladin;
import spacemercs.ui.StarterDeckSelectUI;

@SuppressWarnings("unused")
public class StarterDeckSelectPatch {
    @SpirePatch2(clz= CharacterOption.class, method = "renderRelics")
    public static class RenderStarterDeckSelection {
        @SpirePostfixPatch
        public static void patch(CharacterOption __instance, SpriteBatch sb, float ___infoX) {
            if(__instance.c instanceof Cosmopaladin && __instance.selected) {
                StarterDeckSelectUI.render(sb, ___infoX);
            }
        }
    }

    @SpirePatch2(clz= CharacterOption.class, method = "update")
    public static class UpdateStarterDeckSelection {
        @SpirePostfixPatch
        public static void patch(CharacterOption __instance, float ___infoX) {
            if(__instance.c instanceof Cosmopaladin && __instance.selected) {
                StarterDeckSelectUI.update(___infoX);
            }
        }
    }
}
