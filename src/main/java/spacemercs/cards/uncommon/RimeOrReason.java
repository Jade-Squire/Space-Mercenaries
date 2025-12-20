package spacemercs.cards.uncommon;

import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.SpaceMercsCustomTags;
import spacemercs.SpaceMercsMod;
import spacemercs.cards.BaseCard;
import spacemercs.cards.actions.ApplyCardModifierAction;
import spacemercs.cards.modifiers.ModifyCostUntilPlayedModifier;
import spacemercs.character.Cosmopaladin;
import spacemercs.util.CardStats;

import java.util.List;

@SuppressWarnings("unused")
public class RimeOrReason extends BaseCard implements ModalChoice.Callback {
    public static final String ID = makeID(RimeOrReason.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private final ModalChoice modal;

    private static final CardGroup slowCards = new CardGroup(CardGroup.CardGroupType.CARD_POOL);

    public RimeOrReason() {
        super(ID, info);
        setExhaust(true);

        modal = new ModalChoiceBuilder()
                .setCallback(this)
                .setColor(CardColor.BLUE)
                .addOption("Add a random card that inflicts Slow to your hand.", CardTarget.NONE)
                .setColor(CardColor.COLORLESS)
                .addOption("Add a random colorless card to your hand.", CardTarget.NONE)
                .create();

        initializeSlowCards();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        modal.open();
    }

    @Override
    public List<TooltipInfo> getCustomTooltips() {
        return modal.generateTooltips();
    }


    @Override
    public void optionSelected(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster, int i) {
        AbstractCard card;
        switch(i) {
            case 0:
                card = getRandomSlowCard();
                break;
            case 1:
                card = AbstractDungeon.returnColorlessCard();
                break;
            default:
                card = null;
                break;
        }
        if(card != null) {
            if(upgraded) {
                card.cost = 0;
                card.costForTurn = 0;
                card.isCostModified = true;
            }
            addToBot(new MakeTempCardInHandAction(card));
            if(!upgraded) {
                addToBot(new ApplyCardModifierAction(card, new ModifyCostUntilPlayedModifier(0)));
            }
        }
    }

    private AbstractCard getRandomSlowCard() {
        return slowCards.getRandomCard(true).makeCopy();
    }

    private void initializeSlowCards() {
        for(AbstractCard card : SpaceMercsMod.cosmopaladinCards) {
            if(card.hasTag(SpaceMercsCustomTags.APLLIESSLOW)) {
                slowCards.addToBottom(card);
            }
        }
    }
}
