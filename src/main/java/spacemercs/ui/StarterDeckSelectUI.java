package spacemercs.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import spacemercs.character.Cosmopaladin;

import static spacemercs.SpaceMercsMod.makeID;

public class StarterDeckSelectUI {
    private static final UIStrings UIStrings = CardCrawlGame.languagePack.getUIString(makeID(StarterDeckSelectUI.class.getSimpleName()));

    private static final Hitbox selectLeftHb = new Hitbox(70F * Settings.scale, 70F * Settings.scale);
    private static final Hitbox selectRightHb = new Hitbox(70F * Settings.scale, 70F * Settings.scale);

    private static int currSelection = 1;

    private static final Color[] colors = {Color.ORANGE, Color.CYAN, Color.PURPLE, Color.BLUE};

    public static void render(SpriteBatch sb, float currX) {
        FontHelper.renderFontLeft(sb, FontHelper.buttonLabelFont, UIStrings.TEXT[0], currX - (Settings.WIDTH * 0.02F), Settings.HEIGHT * 0.78F, Settings.GOLD_COLOR);
        if(selectLeftHb.hovered) {
            sb.setColor(Color.WHITE);
        } else {
            sb.setColor(Color.LIGHT_GRAY);
        }
        sb.draw(ImageMaster.CF_LEFT_ARROW, selectLeftHb.cX - selectLeftHb.width/2, selectLeftHb.cY - selectLeftHb.height/2, selectLeftHb.width, selectLeftHb.height);
        FontHelper.renderFontCentered(sb, FontHelper.charDescFont, UIStrings.TEXT[currSelection], selectLeftHb.cX + ((selectRightHb.cX - selectLeftHb.cX) / 2), Settings.HEIGHT * 0.72F, colors[currSelection - 1]);
        if(selectRightHb.hovered) {
            sb.setColor(Color.WHITE);
        } else {
            sb.setColor(Color.LIGHT_GRAY);
        }
        sb.draw(ImageMaster.CF_RIGHT_ARROW, selectRightHb.cX - selectRightHb.width/2, selectRightHb.cY - selectRightHb.height/2, selectRightHb.width, selectRightHb.height);
    }

    public static void update(float currX) {
        selectLeftHb.move(currX, Settings.HEIGHT * 0.72F);
        selectRightHb.move(selectLeftHb.width +  currX + FontHelper.getWidth(FontHelper.charDescFont, UIStrings.TEXT[4], Settings.scale), Settings.HEIGHT * 0.72F);

        selectLeftHb.update();
        selectRightHb.update();

        if(InputHelper.justClickedLeft) {
            if(selectLeftHb.hovered) {
                modifySelection(-1);

            } else if (selectRightHb.hovered) {
                modifySelection(1);
            }
        }
    }

    private static void modifySelection(int amount) {
        currSelection += amount;
        if(currSelection > 4) {
            currSelection = 1;
        }
        if(currSelection < 1) {
            currSelection = 4;
        }
        Cosmopaladin.SUBCLASS = Cosmopaladin.Subclass.values()[currSelection - 1];
    }
}
