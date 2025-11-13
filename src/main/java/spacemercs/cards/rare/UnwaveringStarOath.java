package spacemercs.cards.rare;

import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.SpawnModificationCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import spacemercs.cards.BaseCard;
import spacemercs.character.Cosmopaladin;
import spacemercs.powers.SuppressPower;
import spacemercs.util.CardStats;

import java.util.ArrayList;

@SuppressWarnings("unused")
@NoCompendium
public class UnwaveringStarOath extends BaseCard implements SpawnModificationCard {
    public static final String ID = makeID(UnwaveringStarOath.class.getSimpleName());
    private static final CardStats info = new CardStats(
            Cosmopaladin.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            3
    );

    private static final int SUPPRESS_STACKS = 3;
    private static final int UPG_SUPPRESS_STACKS = 1;
    private static final int DAMAGE = 13;
    private static final int UPG_DAMAGE = 5;

    public UnwaveringStarOath() {
        super(ID, info);

        setMagic(SUPPRESS_STACKS, UPG_SUPPRESS_STACKS);
        setDamage(DAMAGE, UPG_DAMAGE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new SuppressPower(m, magicNumber)));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    @Override
    public boolean canSpawn(ArrayList<AbstractCard> currentRewardCards) { return false;}

    public void replaceSelf(AbstractCard newCard) {
        if(upgraded) {
            newCard.upgrade();
        }
        AbstractDungeon.player.masterDeck.addToTop(newCard);
        AbstractDungeon.player.masterDeck.removeCard(this);
    }
}
