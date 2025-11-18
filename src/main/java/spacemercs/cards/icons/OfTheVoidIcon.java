package spacemercs.cards.icons;

import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;
import spacemercs.util.TextureLoader;

import static spacemercs.SpaceMercsMod.imagePath;
import static spacemercs.SpaceMercsMod.makeID;

public class OfTheVoidIcon extends AbstractCustomIcon {
    public static final String ID = makeID("OfTheVoid");
    private static OfTheVoidIcon singleton;

    public OfTheVoidIcon() {
        super(ID, TextureLoader.getTexture(imagePath("icons/OfTheVoidIcon.png")));
    }

    public static OfTheVoidIcon get() {
        if (singleton == null) {
            singleton = new OfTheVoidIcon();
        }
        return singleton;
    }
}
