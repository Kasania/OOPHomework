package com.kasania.graphics.scenes;

import com.kasania.core.game.settings.GameDifficulty;
import com.kasania.core.game.settings.GameSettings;
import com.kasania.graphics.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.function.BiConsumer;

public class LevelSelectPanel extends ScenePanel{

    private JPanel outerButtonWrapperPanel;
    private JPanel innerButtonWrapperPanel;


    private JButton[] levelButton;

    private JButton backToStartButton;

    public LevelSelectPanel(){
        initializeComponents();
        bindEvent();
        makeView();
    }

    private void initializeComponents(){
        int difficultyNum = GameDifficulty.values().length;
        contentPanel = new JPanel();

        contentPanel.setLayout(new BorderLayout(60,10));
        outerButtonWrapperPanel = new JPanel(new GridLayout(1,3,5,10));
        innerButtonWrapperPanel = new JPanel(new GridLayout(difficultyNum+1,1,5,15));

        levelButton = new JButton[difficultyNum];

        int LevelFontSize = (int) (GameSettings.getInstance().getSettingValue(GameSettings.Items.WINDOW_WIDTH)/(3.5*difficultyNum));

        for(int i = 0; i<difficultyNum; ++i){

            JButton button = new JButton();
            button.setFont(new Font("Serif",Font.PLAIN, LevelFontSize));
            button.setText(GameDifficulty.values()[i].toString().replace("_"," "));

            levelButton[i] = button;
        }

        backToStartButton = new JButton("Back");
        backToStartButton.setFont(new Font("Serif",Font.PLAIN,LevelFontSize));
    }

    private void bindEvent(){

        backToStartButton.addActionListener((e)->{
            sceneChange.accept(Scene.MAIN_MENU,0);
        });

        for(int i = 0; i< levelButton.length; ++i){
            int level = i;
            levelButton[i].addActionListener((e)->{
                sceneChange.accept(Scene.GAME,level);
            });
        }


    }

    private void makeView(){

        for(JButton button : levelButton){
            innerButtonWrapperPanel.add(button);
        }
        innerButtonWrapperPanel.add(backToStartButton);

        outerButtonWrapperPanel.add(new JLabel());
        outerButtonWrapperPanel.add(innerButtonWrapperPanel);
        outerButtonWrapperPanel.add(new JLabel());
        contentPanel.add(new JPanel(),BorderLayout.NORTH);
        contentPanel.add(outerButtonWrapperPanel,BorderLayout.CENTER);
        contentPanel.add(new JPanel(),BorderLayout.SOUTH);
        contentPanel.add(new JPanel(),BorderLayout.EAST);
        contentPanel.add(new JPanel(),BorderLayout.WEST);

    }

    @Override
    public ScenePanel prepareScene(int arg) {

        return this;
    }

    @Override
    public JPanel getContent() {
        return contentPanel;
    }
}
