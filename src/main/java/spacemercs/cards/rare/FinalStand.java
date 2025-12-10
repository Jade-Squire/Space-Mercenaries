package spacemercs.cards.rare;

import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.unique.ExpertiseAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.cards.actions.DiscardBatchAction;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.WillPower;
import spacemercs.util.CardStats;

import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class FinalStand extends BaseCard {
    public static final String ID = makeID(FinalStand.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            1
    );

    private static final int ENERGY_GAIN = 3;
    private static final int WILL_GAIN = 3;
    private static final float BELOW_HEALTH = 0.25F;

    public FinalStand() {
        super(ID, info);
        setExhaust(true);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if(upgraded) {
            Consumer<List<AbstractCard>> consumer = list -> addToTop(new DiscardBatchAction(p, p, list));
            addToBot(new SelectCardsInHandAction(99, "Discard.",true, true, (c) -> true, consumer));
        }
        addToBot(new ExpertiseAction(p, 10));
        addToBot(new GainEnergyAction(ENERGY_GAIN));
        addToBot(new ApplyPowerAction(p, p, new WillPower(p, WILL_GAIN)));
    }

    @Override
    public boolean cardPlayable(AbstractMonster m) {
        if(!((float) AbstractDungeon.player.currentHealth / AbstractDungeon.player.maxHealth <= BELOW_HEALTH)){
            cantUseMessage = "I'm not below 25% health.";
            return false;
        }
        return super.cardPlayable(m);
    }
}
