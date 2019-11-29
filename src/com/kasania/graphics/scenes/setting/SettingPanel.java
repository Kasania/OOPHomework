package com.kasania.graphics.scenes.setting;

import com.kasania.graphics.MainFrame;
import com.kasania.graphics.scenes.ScenePanel;

import javax.swing.*;
import java.awt.*;

public class SettingPanel extends ScenePanel {

    private JButton button1;
    private JButton button2;
    //Todo : Frame rate
    private Runnable reloadFrame;
    private boolean needReload;

    public SettingPanel(){
        initializeComponents();
    }

    private void initializeComponents(){

        WindowMagnificationSettingPanel magnificationSettingPanel = new WindowMagnificationSettingPanel();
        contentPanel = new JPanel(new GridLayout(5,2,5,5));
        button1 = new JButton("Back");
        needReload = false;
        button1.addActionListener((e)->{
            if(needReload){
                if(MainFrame.requestQuestionDialog("Need Restart. ","Restart")){
                    reloadFrame.run();
                }
                else{
                    MainFrame.requestConfirmDialog("Change will apply after Restart.","Change is Not Apply.");
                }

            }

            sceneChange.accept(Scene.MAIN_MENU,0);
        });

        contentPanel.add(button1);
        button1 = new JButton("apply");

        button1.addActionListener((e)->{
            if(magnificationSettingPanel.applyChange()){
                needReload = true;
            }
        });

        contentPanel.add(button1);
        contentPanel.add(magnificationSettingPanel.getContents());
        fillBlank(10-contentPanel.getComponentCount());
    }

    private void fillBlank(int blankCount){

        for(int i = 0; i<blankCount;++i)
            contentPanel.add(new JPanel());
    }

    public void addReloadFrame(Runnable reload){
        reloadFrame = reload;
    }

}
