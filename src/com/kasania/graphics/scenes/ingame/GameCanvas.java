package com.kasania.graphics.scenes.ingame;

import com.kasania.core.game.WordItem;
import com.kasania.core.game.settings.GameSettings;

import java.awt.*;

public class GameCanvas {

    private Canvas wordCanvas;
    private Graphics2D drawGraphics;
    private Font font;

    private long WidthDiv;
    private long HeightDiv;

    GameCanvas(){
        wordCanvas = new Canvas();
        font = new Font("Serif",Font.PLAIN, GameSettings.getInstance().getSettingValue(GameSettings.Items.WINDOW_WIDTH)/55);
        wordCanvas.setBackground(new Color(240,255,240));
        WidthDiv = GameSettings.getInstance().getSettingValue(GameSettings.Items.WINDOW_WIDTH) /100;
        HeightDiv = GameSettings.getInstance().getSettingValue(GameSettings.Items.WINDOW_HEIGHT)/100;
    }

    void prepareGraphics(){
        wordCanvas.createBufferStrategy(2);
    }

    public void drawWords(java.util.List<WordItem> wordSet){

        drawGraphics= (Graphics2D) wordCanvas.getBufferStrategy().getDrawGraphics();

        drawGraphics.clearRect(0,0,
                GameSettings.getInstance().getSettingValue(GameSettings.Items.WINDOW_WIDTH),
                GameSettings.getInstance().getSettingValue(GameSettings.Items.WINDOW_HEIGHT));

        drawGraphics.setFont(font);

        wordSet.forEach(this::draw);

        drawGraphics.dispose();
        wordCanvas.getBufferStrategy().show();

    }

    private void draw(WordItem item){

        drawGraphics.drawString(item.getWord(), WidthDiv*item.getXPosition(), HeightDiv * item.getYPosition());
    }

    Canvas getContent(){
        return wordCanvas;
    }
}
