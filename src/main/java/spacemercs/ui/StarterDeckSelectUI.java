package spacemercs.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import spacemercs.SpaceMercsMod;
import spacemercs.character.Cosmopaladin;

import java.util.ArrayList;

import static spacemercs.SpaceMercsMod.makeID;

public class StarterDeckSelectUI {
    private static final UIStrings UIStrings = CardCrawlGame.languagePack.getUIString(makeID(StarterDeckSelectUI.class.getSimpleName()));

    private static final Hitbox selectLeftHb = new Hitbox(70F * Settings.scale, 70F * Settings.scale);
    private static final Hitbox selectRightHb = new Hitbox(70F * Settings.scale, 70F * Settings.scale);

    private static final ArrayList<AbstractCard> cardPool = new ArrayList<>();
    private static ArrayList<String> currDeck = new ArrayList<>();

    private static int currSelection = 1;

    private static final Color[] colors = {Color.ORANGE, Color.CYAN, Color.PURPLE, Color.BLUE};

    public static void render(SpriteBatch sb, float currX) {
        StarterDeckSelectUI.initializeCardPool();
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
        float x = Settings.WIDTH * 0.21f;
        float y = Settings.HEIGHT;
        AbstractCard selectedCard = null;
        String prevId = "";
        int count = 1;
        for(String id : currDeck) {
            // idk why, but if you don't render this dummy card offscreen, the bottom text in character select
            if(id.equals(currDeck.get(currDeck.size()-1))) {
                selectedCard = new VoidCard();
                selectedCard.current_x = Settings.WIDTH * 2;
                selectedCard.drawScale = 1;
                selectedCard.render(sb);
                break;
            }
            if(id.equals(prevId)) {
                count++;
                continue;
            } else if(count > 1 && selectedCard != null) {
                FontHelper.renderFontCentered(sb, FontHelper.charDescFont, "x" + count, selectedCard.current_x, selectedCard.current_y - (selectedCard.hb.height / 2.f), Settings.CREAM_COLOR);
                count = 1;
            } else {
                count = 1;
            }
            prevId = id;
            selectedCard = null;
            for(AbstractCard card : cardPool) {
                if(card.cardID.equals(id)) {
                    selectedCard = card.makeCopy();
                    break;
                }
            }
            if(selectedCard != null) {
                selectedCard.drawScale = 0.55f;
                selectedCard.current_x = x + selectedCard.hb.width / 2.f;
                selectedCard.current_y = y - selectedCard.hb.height / 2.f;
                selectedCard.render(sb);
                x += selectedCard.hb.width * 0.89f;
            }
        }


        /*for(int i = 0; i < 5; i++) {
            for(int j = 0; j < 5; j++) {
                if((i*5)+j < starterCards.size()) {
                    selectedCard = starterCards.get((i*5)+j).makeCopy();
                    selectedCard.drawScale = 0.5f;
                    selectedCard.current_x = x + selectedCard.hb.width / 2.f;
                    selectedCard.current_y = y - selectedCard.hb.height / 2.f;
                    selectedCard.render(sb);
                }
                if(selectedCard == null)
                {
                    break;
                }
                x += selectedCard.hb.width + (Settings.WIDTH * 0.01f);
            }
            if(selectedCard == null)
            {
                break;
            }
            x = 0.0f;
            y -= selectedCard.hb.height + (Settings.HEIGHT * 0.01f);
        }*/
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
        setDeck();
    }

    private static void initializeCardPool() {
        if(!cardPool.isEmpty()) {
            return;
        }
        for(AbstractCard c : SpaceMercsMod.cosmopaladinCards) {
            if(!cardPool.contains(c)) {
                cardPool.add(c);
            }
        }
        setDeck();
    }

    private static void setDeck() {
        currDeck = Cosmopaladin.getStartingDeckStatic();
        currDeck.add("Dummy");
    }
}
