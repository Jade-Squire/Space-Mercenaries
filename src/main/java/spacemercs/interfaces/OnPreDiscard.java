package spacemercs.interfaces;


public interface OnPreDiscard {
    default boolean onDiscard(int count) {return false;}
}
