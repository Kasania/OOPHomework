package com.kasania.graphics.scenes.setting;

import com.kasania.core.game.settings.GameSettings;
import com.kasania.graphics.MainFrame;
import com.kasania.graphics.scenes.ScenePanel;

import javax.swing.*;
import java.awt.*;

public class SettingPanel extends ScenePanel {

    //Todo : Frame rate
    private Runnable reloadFrame;
    private boolean needReload;

    public SettingPanel(){
        initializeComponents();
    }

    private void initializeComponents(){
        Font font = new Font("Serif",Font.PLAIN,
                GameSettings.getInstance().getSettingValue(GameSettings.Items.WINDOW_HEIGHT)/30);

        WindowMagnificationSettingPanel magnificationSettingPanel = new WindowMagnificationSettingPanel();
        FrameRateSettingPanel frameRateSettingPanel = new FrameRateSettingPanel();
        contentPanel = new JPanel(new BorderLayout(15,15));

        JPanel innerContentPanel = new JPanel(new GridLayout(4,2,5,5));
        needReload = false;

        JButton backToMainMenuButton = new JButton("Back to Menu");
        backToMainMenuButton.addActionListener((e)->{
            if(needReload){
                if(MainFrame.requestQuestionDialog("Need Restart. ","Restart")){
                    reloadFrame.run();
                }
                else{
                    MainFrame.requestConfirmDialog("Change will apply after Restart.","Change is Not Applied.");
                }

            }

            sceneChange.accept(Scene.MAIN_MENU,0);
        });

        JButton applyChangeButton = new JButton("Apply");
        applyChangeButton.addActionListener((e)->{
            if(magnificationSettingPanel.applyChange()){
                needReload = true;
            }

            frameRateSettingPanel.applyChange();

        });

        backToMainMenuButton.setFont(font);

        applyChangeButton.setFont(font);

        innerContentPanel.add(magnificationSettingPanel.getContents());
        innerContentPanel.add(frameRateSettingPanel.getContents());

        innerContentPanel.add(applyChangeButton);
        innerContentPanel.add(backToMainMenuButton);

        fillBlank(innerContentPanel,8-innerContentPanel.getComponentCount());

        contentPanel.add(innerContentPanel, BorderLayout.CENTER);
        contentPanel.add(new JPanel(), BorderLayout.EAST);
        contentPanel.add(new JPanel(), BorderLayout.NORTH);
        contentPanel.add(new JPanel(), BorderLayout.SOUTH);
        contentPanel.add(new JPanel(), BorderLayout.WEST);


    }

    private void fillBlank(JPanel panel , int blankCount){

        for(int i = 0; i<blankCount;++i)
            panel.add(new JPanel());
    }

    public void addReloadFrame(Runnable reload){
        reloadFrame = reload;
    }

}
