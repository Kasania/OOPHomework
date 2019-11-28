package com.kasania.core.game;

public class WordItem {

    private float xPosition;
    private float yPosition;
    private String word;
    private boolean isCaught;

    public WordItem(int x, int y, String word){
        this.xPosition = x;
        this.yPosition = y;
        this.word = word;
        isCaught = false;
    }

    public float getXPosition() {
        return xPosition;
    }

    public float getYPosition() {
        return yPosition;
    }

    public void increaseYPosition(float offset){
        yPosition += offset;
    }

    public String getWord() {
        return word;
    }

    public void setCaught(){
        this.isCaught = true;
    }

    public boolean isCaught() {
        return this.isCaught;
    }

    @Override
    public String toString() {
        return xPosition + ", " + yPosition + ", " + word;
    }


}
