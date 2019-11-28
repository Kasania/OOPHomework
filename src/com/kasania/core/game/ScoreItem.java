package com.kasania.core.game;

public class ScoreItem {

    public final String name;
    public final int difficulty;
    public final long score;

    public ScoreItem(final String name,final int difficulty, final long score){
        this.name = name;
        this.difficulty = difficulty;
        this.score = score;
    }

    @Override
    public String toString() {
        return name + ":" +difficulty + ":" + score;
    }


}
