package com.kasania.graphics.scenes;

import com.kasania.core.game.settings.GameSettings;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel extends AbstractScenePanel {

    private JPanel outerButtonWrapperPanel;
    private JPanel innerButtonWrapperPanel;
    private JLabel gameTitleLabel;

    private JButton gameStartButton;
    private JButton wordManagerButton;
    private JButton settingButton;

    private JButton exitButton;
    private Runnable onExit;

    public MainMenuPanel(){
        initializeComponents();
        bindEvents();
        makeView();
    }

    private void makeView() {
        innerButtonWrapperPanel.add(gameStartButton);
        innerButtonWrapperPanel.add(wordManagerButton);
        innerButtonWrapperPanel.add(settingButton);
        innerButtonWrapperPanel.add(exitButton);

        outerButtonWrapperPanel.add(new JLabel());
        outerButtonWrapperPanel.add(innerButtonWrapperPanel);
        outerButtonWrapperPanel.add(new JLabel());

        contentPanel.add(gameTitleLabel,BorderLayout.NORTH);
        contentPanel.add(outerButtonWrapperPanel,BorderLayout.CENTER);
        contentPanel.add(new JPanel(),BorderLayout.SOUTH);
    }

    private void initializeComponents(){

        int numOfButtons = 4;

        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(5,40));

        int titleFontSize = GameSettings.getInstance().getSettingValue(GameSettings.Items.WINDOW_WIDTH)/7;
        gameTitleLabel = new JLabel("Word Crasher");
        gameTitleLabel.setFont(new Font("Serif",Font.BOLD,titleFontSize));
        gameTitleLabel.setHorizontalAlignment(JLabel.CENTER);

        outerButtonWrapperPanel = new JPanel();
        outerButtonWrapperPanel.setLayout(new GridLayout(1,3 ));
        outerButtonWrapperPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        innerButtonWrapperPanel = new JPanel();
        innerButtonWrapperPanel.setLayout(new GridLayout(numOfButtons,1,5,15));

        int startFontSize = GameSettings.getInstance().getSettingValue(GameSettings.Items.WINDOW_WIDTH)/(4*numOfButtons);
        gameStartButton = new JButton("Start");
        gameStartButton.setFont(new Font("Serif",Font.PLAIN,startFontSize));

        int wordManagerFontSize = (int) (GameSettings.getInstance().getSettingValue(GameSettings.Items.WINDOW_WIDTH)/(5.2*numOfButtons));
        wordManagerButton = new JButton("Manage Word");
        wordManagerButton.setFont(new Font("Serif",Font.PLAIN,wordManagerFontSize));

        int settingFontSize = (int) (GameSettings.getInstance().getSettingValue(GameSettings.Items.WINDOW_WIDTH)/(5.5*numOfButtons));
        settingButton = new JButton("Setting");
        settingButton.setFont(new Font("Serif",Font.PLAIN,settingFontSize));

        int exitFontSize = (int) (GameSettings.getInstance().getSettingValue(GameSettings.Items.WINDOW_WIDTH)/(4.5*numOfButtons));
        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Serif",Font.PLAIN,exitFontSize));

    }

    private void bindEvents(){
        gameStartButton.addActionListener((e)->{
            sceneChange.accept(Scene.PLAYER_MODE_SELECT,0);
        });

        wordManagerButton.addActionListener((e)->{
            sceneChange.accept(Scene.WORD_MANAGER,0);
        });

        settingButton.addActionListener((e)->{
            sceneChange.accept(Scene.SETTING,0);
        });

        exitButton.addActionListener((e)-> onExit.run());

    }


    public void onExit(Runnable onExit){
        this.onExit = onExit;
    }

}
