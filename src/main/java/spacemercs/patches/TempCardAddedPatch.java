package spacemercs.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import spacemercs.interfaces.OnCreateCard;
import spacemercs.powers.HungerPower;

@SuppressWarnings("unused")
public class TempCardAddedPatch {
    @SpirePatch2(clz = ShowCardAndAddToDrawPileEffect.class, method=SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCard.class, float.class, float.class, boolean.class, boolean.class, boolean.class})
    @SpirePatch2(clz = ShowCardAndAddToDrawPileEffect.class, method=SpirePatch.CONSTRUCTOR, paramtypez = {AbstractCard.class, boolean.class, boolean.class})
    public static class TempCardAddedToDraw {
        public static void Postfix(AbstractGameEffect __instance, AbstractCard srcCard) {
            if(AbstractDungeon.player.hasPower(HungerPower.POWER_ID)) {
                AbstractDungeon.player.powers.stream().filter(power -> power instanceof OnCreateCard).forEach(power -> ((OnCreateCard)power).onCreateCard(srcCard));
            }
        }
    }
}
