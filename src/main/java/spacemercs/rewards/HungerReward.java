package spacemercs.rewards;

import basemod.abstracts.CustomReward;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import spacemercs.cards.rare.Starvation;

public class HungerReward extends CustomReward {
    private static final Texture ICON = ImageMaster.REWARD_CARD_NORMAL;

    public HungerReward() {
        super(ICON, "Add a special card to your deck", HungerRewardType.HUNGER_REWARD_TYPE);
        this.cards.add(new Starvation());
    }

    @Override
    public boolean claimReward() {
        AbstractDungeon.cardRewardScreen.open(this.cards, this, TEXT[4]);
        AbstractDungeon.previousScreen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
        return false;
    }
}
