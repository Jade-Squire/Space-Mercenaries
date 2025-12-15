package spacemercs.interfaces;

import com.megacrit.cardcrawl.monsters.AbstractMonster;

public interface OnMonsterDeath {
    default void onMonsterDeath(AbstractMonster m) {}
}
