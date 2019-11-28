package com.kasania.graphics.scenes.ingame;

import com.kasania.core.game.settings.GameSettings;

import javax.swing.*;
import java.awt.*;

public class GameLifePanel {

    private JPanel content;
    private JLabel lifeTitleLabel;
    private Canvas lifeIndicateCanvas;
    private int canvasWidth;
    private int canvasHeight;

    private Graphics2D drawGraphics;


    GameLifePanel(){

        initializeComponent();

    }


    private void initializeComponent(){

        content = new JPanel(new GridLayout(2,1));

        lifeTitleLabel = new JLabel("Left Life");
        lifeTitleLabel.setHorizontalAlignment(JLabel.CENTER);
        lifeTitleLabel.setFont(new Font("Serif", Font.PLAIN, GameSettings.getInstance().getSettingValue(GameSettings.Items.WINDOW_WIDTH) /25));
        lifeIndicateCanvas = new Canvas();
        lifeIndicateCanvas.setPreferredSize(new Dimension(293,GameSettings.getInstance().getSettingValue(GameSettings.Items.WINDOW_WIDTH)/15));
        lifeIndicateCanvas.setBackground(Color.WHITE);

        content.add(lifeTitleLabel);
        content.add(lifeIndicateCanvas);

    }

    void prepareGraphics(){
        lifeIndicateCanvas.createBufferStrategy(2);

    }

    public void updateLifePanel(int maxLife, int currentLife){

        drawGraphics = (Graphics2D) lifeIndicateCanvas.getBufferStrategy().getDrawGraphics();

        drawLifeGauge(maxLife, currentLife);

        drawGraphics.dispose();
        lifeIndicateCanvas.getBufferStrategy().show();

    }

    private void drawLifeGauge(int maxLife, int currentLife){

        canvasWidth = lifeIndicateCanvas.getWidth();
        canvasHeight = lifeIndicateCanvas.getHeight();
        double widthPerUnit = 1.0*canvasWidth / maxLife;

        drawGraphics.setColor(Color.WHITE);
        drawGraphics.clearRect(0,0,canvasWidth,canvasHeight);

        drawGraphics.setColor(Color.RED);
        drawGraphics.fillRect(0,0, (int) (widthPerUnit* currentLife), canvasHeight);

        drawGraphics.setColor(Color.BLACK);
        for(int i = 0; i<=maxLife; ++i){
            drawGraphics.drawLine((int) (widthPerUnit*i),0,(int) (widthPerUnit*i),canvasHeight);
        }


    }

    JPanel getContent(){
        return content;
    }

}
