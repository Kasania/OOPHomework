package com.kasania.graphics.scenes;

import com.kasania.core.game.settings.GameSettings;

import javax.swing.*;
import java.awt.*;

public class PlayerModeSelectPanel extends ScenePanel {

    private JButton singleModeButton;
    private JButton multiModeButton;
    private JButton backToStartButton;

    public PlayerModeSelectPanel(){
        contentPanel = new JPanel(new BorderLayout(20,20));
        JPanel buttonWrapperPanel = new JPanel(new BorderLayout(15,15));
        JPanel modeSelectButtonWrapperPanel = new JPanel(new GridLayout(1,2,15,15));

        Font modeSelectFont = new Font("Serif",Font.ITALIC, GameSettings.getInstance().getSettingValue(GameSettings.Items.WINDOW_WIDTH)/10);

        backToStartButton = new JButton("<html><center>Back<br>to<br>Menu</center></html>");
        backToStartButton.setFont(new Font("Serif",Font.ITALIC, GameSettings.getInstance().getSettingValue(GameSettings.Items.WINDOW_WIDTH)/20));

        singleModeButton = new JButton("<html>Single<br>Mode</html>");
        singleModeButton.setFont(modeSelectFont);
        multiModeButton = new JButton("<html>Multi<br>Mode</html>");
        multiModeButton.setFont(modeSelectFont);

        modeSelectButtonWrapperPanel.add(singleModeButton);
        modeSelectButtonWrapperPanel.add(multiModeButton);

        buttonWrapperPanel.add(backToStartButton,BorderLayout.WEST);
        buttonWrapperPanel.add(modeSelectButtonWrapperPanel,BorderLayout.CENTER);
        contentPanel.add(buttonWrapperPanel,BorderLayout.CENTER);

        contentPanel.add(new JLabel(),BorderLayout.WEST);
        contentPanel.add(new JLabel(),BorderLayout.EAST);
        contentPanel.add(new JLabel(),BorderLayout.SOUTH);
        contentPanel.add(new JLabel(),BorderLayout.NORTH);


        backToStartButton.addActionListener((e)-> sceneChange.accept(Scene.MAIN_MENU,0));
        singleModeButton.addActionListener((e)-> sceneChange.accept(Scene.LEVEL_SELECT,0));
        multiModeButton.addActionListener((e)-> sceneChange.accept(Scene.SETTING,0));

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


