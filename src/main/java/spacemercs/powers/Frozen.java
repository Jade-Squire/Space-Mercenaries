package spacemercs.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import spacemercs.cards.uncommon.ColdDarkGentle;
import spacemercs.util.GeneralUtils;
import spacemercs.util.TextureLoader;

import javax.swing.*;
import java.lang.reflect.Field;

import static spacemercs.SpaceMercsMod.makeID;

public class Frozen extends AbstractPower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(Frozen.class.getSimpleName());
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private byte moveByte;
    private AbstractMonster.Intent moveIntent;
    private EnemyMoveInfo move;
    private boolean attacked = false;

    private static final int WEAK_STACKS = 3;
    private static final int SHATTER_DAMAGE = 20;

    public Frozen(AbstractMonster owner, int amount) {
        this.name = NAME;
        this.ID = "stslib:Stunned";
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
        this.updateDescription();
        loadImage();
    }

    private void loadImage() {
        String unPrefixed = GeneralUtils.removePrefix(POWER_ID);
        Texture normalTexture = TextureLoader.getPowerTexture(unPrefixed);
        Texture hiDefImage = TextureLoader.getHiDefPowerTexture(unPrefixed);
        if (hiDefImage != null)
        {
            region128 = new TextureAtlas.AtlasRegion(hiDefImage, 0, 0, hiDefImage.getWidth(), hiDefImage.getHeight());
            if (normalTexture != null)
                region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
        }
        else
        {
            this.img = normalTexture;
            region48 = new TextureAtlas.AtlasRegion(normalTexture, 0, 0, normalTexture.getWidth(), normalTexture.getHeight());
        }
    }

    public void updateDescription() {
        if (this.amount > 1) {
            this.description = DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0];
        }
        this.description += DESCRIPTIONS[3];
    }

    public void atEndOfRound() {
        if (this.amount <= 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this, 1));
        }

    }

    public void onInitialApplication() {
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {
            public void update() {
                if (Frozen.this.owner instanceof AbstractMonster) {
                    Frozen.this.moveByte = ((AbstractMonster) Frozen.this.owner).nextMove;
                    Frozen.this.moveIntent = ((AbstractMonster) Frozen.this.owner).intent;

                    try {
                        Field f = AbstractMonster.class.getDeclaredField("move");
                        f.setAccessible(true);
                        Frozen.this.move = (EnemyMoveInfo) f.get(Frozen.this.owner);
                        EnemyMoveInfo stunMove = new EnemyMoveInfo(Frozen.this.moveByte, AbstractMonster.Intent.STUN, -1, 0, false);
                        f.set(Frozen.this.owner, stunMove);
                        ((AbstractMonster) Frozen.this.owner).createIntent();
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        ((ReflectiveOperationException) e).printStackTrace();
                    }
                }

                this.isDone = true;
            }
        });

        for(AbstractCard c : AbstractDungeon.player.hand.group) {
            if(c.cardID.equals(ColdDarkGentle.ID)) {
                c.setCostForTurn(0);
            }
        }
    }

    public void onRemove() {
        if (this.owner instanceof AbstractMonster) {
            AbstractMonster m = (AbstractMonster) this.owner;
            if (this.move != null) {
                m.setMove(this.moveByte, this.moveIntent, this.move.baseDamage, this.move.multiplier, this.move.isMultiDamage);
            } else {
                m.setMove(this.moveByte, this.moveIntent);
            }

            m.createIntent();
            m.applyPowers();
        }

        for(AbstractCard c : AbstractDungeon.player.hand.group) {
            if(c.cardID.equals(ColdDarkGentle.ID)) {
                ((ColdDarkGentle)c).checkCost((AbstractMonster)this.owner);
            }
        }
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        this.attacked = (card.type == AbstractCard.CardType.ATTACK && (action.target == owner || card.target == AbstractCard.CardTarget.ALL_ENEMY));
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if(damageAmount > 0 && this.attacked) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this));
            addToBot(new DamageAction(owner, new DamageInfo(owner, SHATTER_DAMAGE, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SMASH));
            addToBot(new ApplyPowerAction(owner, AbstractDungeon.player, new WeakPower(owner, WEAK_STACKS, false)));
        }
        return damageAmount;
    }

    @Override
    public AbstractPower makeCopy() {
        return new Frozen((AbstractMonster) owner, amount);
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
