package spacemercs.cards.actions;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.SpawnModificationCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.ShowCardAndPoofAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class NewPathAction extends AbstractGameAction {
    private final float startingDuration;

    public NewPathAction(AbstractCreature target, AbstractCreature owner) {
        setValues(target, owner);
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startingDuration;

    }

    @Override
    public void update() {
        if (this.duration == this.startingDuration) {
            for(AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                c.stopGlowing();
            }
            AbstractDungeon.gridSelectScreen.open(AbstractDungeon.player.masterDeck, Math.min(3, AbstractDungeon.player.masterDeck.size()), false, "Select cards to remove.");
            AbstractDungeon.gridSelectScreen.forClarity = false;
        } else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for(AbstractCard c : AbstractDungeon.player.masterDeck.group) {
                c.triggerOnGlowCheck();
            }
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                addToTop(new ShowCardAndPoofAction(c));
                AbstractDungeon.player.masterDeck.removeCard(c);
                boolean found = removeCardFromGroup(AbstractDungeon.player.hand, c);
                if(!found) {
                    found = removeCardFromGroup(AbstractDungeon.player.drawPile, c);
                }
                if(!found) {
                    found = removeCardFromGroup(AbstractDungeon.player.discardPile, c);
                }
                if(!found) {
                    removeCardFromGroup(AbstractDungeon.player.exhaustPile, c);
                }
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            addToTop(new WaitAction(0.2f));
            addToTop(new SelectCardAndAddToDeckAction(AbstractDungeon.commonCardPool));
            addToTop(new SelectCardAndAddToDeckAction(AbstractDungeon.uncommonCardPool));
            addToTop(new SelectCardAndAddToDeckAction(modifyRarePool()));
            this.isDone = true;
        }
        this.tickDuration();
    }

    private boolean removeCardFromGroup(CardGroup cardGroup, AbstractCard card) {
        AbstractCard failsafe = null;
        for(AbstractCard c : cardGroup.group) {
            if(c.cardID.equals(card.cardID)) {
                if(c.upgraded == card.upgraded) {
                    cardGroup.removeCard(c);
                    return true;
                } else {
                    failsafe = card;
                }
            }
        }
        if(failsafe != null) {
            cardGroup.removeCard(failsafe);
            return true;
        }
        return false;
    }

    private CardGroup modifyRarePool() {
        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for(AbstractCard c : AbstractDungeon.rareCardPool.group) {
            if(c instanceof SpawnModificationCard) {
                if(((SpawnModificationCard) c).canSpawn(AbstractDungeon.getRewardCards())) {
                    if(((SpawnModificationCard)c).replaceWith(AbstractDungeon.getRewardCards()) != c) {
                        group.addToBottom(((SpawnModificationCard)c).replaceWith(AbstractDungeon.getRewardCards()));
                    } else {
                        group.addToBottom(c.makeCopy());
                    }
                }
            } else {
                group.addToBottom(c.makeCopy());
            }
        }

        return group;
    }
}
