package spacemercs.cards.uncommon;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.SpaceMercsCustomTags;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.SuppressPower;
import spacemercs.util.CardStats;

@SuppressWarnings("unused")
public class SuppressionGrenade extends BaseCard {
    public static final String ID = makeID(SuppressionGrenade.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            2
    );

    private static final int SUPPRESS_STACKS = 1;
    protected boolean isCopy = false;

    public SuppressionGrenade() {
        super(ID, info);
        tags.add(SpaceMercsCustomTags.GRENADE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for(int i = 0; i < energyOnUse; i++) {
            addToBot(new ApplyPowerAction(m, p, new SuppressPower(m, SUPPRESS_STACKS)));
        }

        if(!isCopy && upgraded) {
            AbstractCard tmp = this.makeSameInstanceOf();
            AbstractDungeon.player.limbo.addToBottom(tmp);
            tmp.current_x = this.current_x;
            tmp.current_y = this.current_y;
            tmp.target_x = (float) Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = (float) Settings.HEIGHT / 2.0F;
            if (m != null) {
                tmp.calculateCardDamage(m);
            }

            tmp.purgeOnUse = true;
            ((SuppressionGrenade)tmp).isCopy = true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, this.energyOnUse, true, true), true);
        }
    }
}
