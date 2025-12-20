package spacemercs.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import spacemercs.cards.actions.GainRelicAction;
import spacemercs.cards.actions.RemoveRelicAction;
import spacemercs.character.Cosmopaladin;

import static spacemercs.SpaceMercsMod.makeID;

public class BrokenBlade extends BaseRelic implements ClickableRelic {
    private static final String NAME = BrokenBlade.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.

    private boolean activatedThisFight = false;
    private static final int DAMAGE = 4;
    private static final int OBSIDIAN_TO_UPGRADE = 5;

    public BrokenBlade() {
        super(ID, NAME, Cosmopaladin.Meta.CARD_COLOR, RARITY, SOUND);
        this.counter = 0;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + DAMAGE + DESCRIPTIONS[1];
    }

    @Override
    public void onVictory() {
        activatedThisFight = false;
    }

    @Override
    public void onRightClick() {
        if(activatedThisFight || AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead() || AbstractDungeon.getCurrRoom().monsters.monsters.isEmpty()) {
            return;
        }
        activatedThisFight = true;
        int currMinHp = Integer.MAX_VALUE;
        AbstractCreature target = null;
        for(AbstractCreature c : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if(c.currentHealth < currMinHp) {
                currMinHp = c.currentHealth;
                target = c;
            }
        }
        if(target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(target.hb.cX, target.hb.cY, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
            target.damage(new DamageInfo(AbstractDungeon.player, DAMAGE, DamageInfo.DamageType.THORNS));
            if ((target.isDying || target.currentHealth <= 0) && !target.halfDead && !target.hasPower("Minion")) {
                this.counter += 3;
                if(counter >= OBSIDIAN_TO_UPGRADE) {
                    upgradeRelic();
                }
            }
        }
    }

    private void upgradeRelic() {
        addToTop(new GainRelicAction(AbstractDungeon.player, AbstractDungeon.player, currentX, currentY, new ObsidianSword()));
        addToTop(new RemoveRelicAction(AbstractDungeon.player, AbstractDungeon.player, ID));
    }
}
