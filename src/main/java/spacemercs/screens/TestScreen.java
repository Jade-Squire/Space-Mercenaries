package spacemercs.screens;

import basemod.abstracts.CustomScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;

import java.awt.*;

@SuppressWarnings("unused")
public class TestScreen extends CustomScreen {
    private static float posX = 0.5f * Settings.WIDTH;
    private static float posY = 0.5f * Settings.HEIGHT;
    private static float time = 0.f;
    private static final Hitbox cancelHb = new Hitbox(0.9F * Settings.WIDTH, 0.25F * Settings.HEIGHT, .1F * Settings.WIDTH, 0.05F * Settings.HEIGHT);

    public static class Enum {
        @SpireEnum
        public static AbstractDungeon.CurrentScreen TEST_SCREEN;
    }

    @Override
    public AbstractDungeon.CurrentScreen curScreen() {
        return Enum.TEST_SCREEN;
    }

    private void open() {
        if(AbstractDungeon.screen != AbstractDungeon.CurrentScreen.NONE) {
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }

        reopen();
    }

    @Override
    public void reopen() {
        AbstractDungeon.overlayMenu.hideBlackScreen();
        AbstractDungeon.screen = curScreen();
        AbstractDungeon.isScreenUp = true;
    }

    @Override
    public void close() {
        if(AbstractDungeon.previousScreen != null) {
            AbstractDungeon.screen = AbstractDungeon.previousScreen;
        } else {
            AbstractDungeon.screen = AbstractDungeon.CurrentScreen.NONE;
            AbstractDungeon.isScreenUp = false;
        }
    }

    @Override
    public void update() {
        time += Gdx.graphics.getDeltaTime();
        posX = 0.5F * Settings.WIDTH + ((float)Math.sin(time) * Settings.WIDTH * .25F);
        posY = 0.5F * Settings.HEIGHT + ((float)Math.cos(time) * Settings.WIDTH * .25F);

        cancelHb.update();

        if(InputHelper.justClickedLeft) {
            if(cancelHb.hovered) {
                AbstractDungeon.closeCurrentScreen();
            }
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.draw(ImageMaster.CHECKBOX, posX, posY, 25.f, 25.f);
        if(cancelHb.hovered) {
            sb.setColor(Color.WHITE);
        } else {
            sb.setColor(Color.BLACK);
        }
        sb.draw(ImageMaster.PROCEED_BUTTON, cancelHb.cX - cancelHb.width, cancelHb.cY - cancelHb.height, cancelHb.width * 2, cancelHb.height * 2);
    }

    @Override
    public void openingSettings() {
        AbstractDungeon.previousScreen = curScreen();
    }

}
