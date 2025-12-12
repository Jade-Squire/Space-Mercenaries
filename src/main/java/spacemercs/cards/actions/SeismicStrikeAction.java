package spacemercs.cards.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import spacemercs.powers.Amp;
import spacemercs.powers.Jolt;

public class SeismicStrikeAction extends AbstractGameAction {
    private final int energyOnUse;
    private final int damage;
    private final boolean upgraded;

    public SeismicStrikeAction(AbstractCreature target, AbstractCreature owner, int energyOnUse, int damage, boolean upgraded) {
        this.setValues(target, owner);
        this.energyOnUse = energyOnUse;
        this.damage = damage;
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        int effect = energyOnUse;
        if(AbstractDungeon.player.hasRelic(ChemicalX.ID)){
            effect += 2;
        }
        if(effect > 0) {
            addToBot(new DamageAction(target, new DamageInfo(AbstractDungeon.player, damage * effect), AttackEffect.SLASH_VERTICAL));
            addToBot(new ApplyPowerAction(target, AbstractDungeon.player, new Jolt(target, effect * 2)));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new Amp(AbstractDungeon.player, effect * 2)));
            if(upgraded) {
                addToBot(new DrawCardAction(effect));
            }
        }
        AbstractDungeon.player.energy.use(EnergyPanel.totalCount);
        this.isDone = true;
    }
}
