package com.kasania.graphics.scenes.ingame;

import com.kasania.core.game.settings.GameSettings;

import javax.swing.*;
import java.awt.*;

public class GameScorePanel {
    private JPanel contentPanel;

    private JLabel currentScoreTitle;
    private JLabel currentScore;

    private JLabel highScoreTitle;
    private JLabel highScore;

    GameScorePanel(){

        initializeComponents();
        makeView();
    }

    private void initializeComponents(){
        Font font = new Font("Serif", Font.PLAIN, GameSettings.getInstance().getSettingValue(GameSettings.Items.WINDOW_WIDTH)/30);

        contentPanel = new JPanel(new GridLayout(2,1,5,10));
//        contentPanel.setBackground(new Color(240,240,255));

        currentScoreTitle = new JLabel(" Current Score ");
        currentScoreTitle.setFont(font);
        currentScoreTitle.setHorizontalAlignment(JLabel.CENTER);
        currentScore = new JLabel();
        currentScore.setFont(font);
        currentScore.setHorizontalAlignment(JLabel.CENTER);
        highScoreTitle = new JLabel(" High Score ");
        highScoreTitle.setFont(font);
        highScoreTitle.setHorizontalAlignment(JLabel.CENTER);
        highScore = new JLabel();
        highScore.setFont(font);
        highScore.setHorizontalAlignment(JLabel.CENTER);

    }

    private void makeView(){

        JPanel currentScorePanel = new JPanel(new GridLayout(2,1,5,5));
        JPanel highScorePanel = new JPanel(new GridLayout(2,1,5,5));

        currentScorePanel.setBackground(new Color(240,255,240));
        highScorePanel.setBackground(new Color(230,230,255));

        currentScorePanel.setBorder(BorderFactory.createLineBorder(Color.GREEN.brighter().brighter(),1));
        highScorePanel.setBorder(BorderFactory.createLineBorder(Color.BLUE.brighter().brighter(),1));

        currentScorePanel.add(currentScoreTitle);
        currentScorePanel.add(currentScore);

        highScorePanel.add(highScoreTitle);
        highScorePanel.add(highScore);

        contentPanel.add(highScorePanel);
        contentPanel.add(currentScorePanel);

    }

    public void setScores(long highScore, long currentScore){
        setHighScore(highScore);
        setCurrentScore(currentScore);
    }

    private void setCurrentScore(long score){
        currentScore.setText(String.valueOf(score));
    }

    private void setHighScore(long score){
        highScore.setText(String.valueOf(score));
    }

    JPanel getContents(){
        return contentPanel;
    }
}
