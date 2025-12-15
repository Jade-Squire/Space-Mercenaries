package spacemercs.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CtBehavior;
import spacemercs.interfaces.OnMonsterDeath;

@SuppressWarnings("unused")
public class OnMonsterDeathPatch {
    @SpirePatch2(clz = AbstractMonster.class, method = "die", paramtypez = {boolean.class})
    public static class MonsterDied {
        @SpireInsertPatch(
                locator = Locator.class
        )
        public static void Insert(AbstractMonster __instance, boolean triggerRelics) {
            if (__instance.currentHealth <= 0 && triggerRelics) {
                for (AbstractPower p : AbstractDungeon.player.powers) {
                    if (p instanceof OnMonsterDeath) {
                        ((OnMonsterDeath) p).onMonsterDeath(__instance);
                    }
                }
                for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
                    if (c instanceof OnMonsterDeath) {
                        ((OnMonsterDeath) c).onMonsterDeath(__instance);
                    }
                }
                for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
                    if (c instanceof OnMonsterDeath) {
                        ((OnMonsterDeath) c).onMonsterDeath(__instance);
                    }
                }
                for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
                    if (c instanceof OnMonsterDeath) {
                        ((OnMonsterDeath) c).onMonsterDeath(__instance);
                    }
                }
                for (AbstractCard c : AbstractDungeon.player.hand.group) {
                    if (c instanceof OnMonsterDeath) {
                        ((OnMonsterDeath) c).onMonsterDeath(__instance);
                    }
                }
                for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                    if (c instanceof OnMonsterDeath) {
                        ((OnMonsterDeath) c).onMonsterDeath(__instance);
                    }
                }
            }
        }

        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractMonster.class, "currentHealth");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}
