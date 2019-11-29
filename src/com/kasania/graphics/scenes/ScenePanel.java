package com.kasania.graphics.scenes;

import jdk.jshell.spi.ExecutionControl;

import javax.swing.*;
import java.util.function.BiConsumer;

public abstract class ScenePanel {

    public enum Scene {
        MAIN_MENU, PLAYER_MODE_SELECT, LEVEL_SELECT, GAME, WORD_MANAGER, SETTING,
    }

    protected JPanel contentPanel;
    protected BiConsumer<Scene,Integer> sceneChange;

    public void addSceneChanger(BiConsumer<Scene, Integer> sceneChanger){
        this.sceneChange = sceneChanger;
    }
    public ScenePanel prepareScene(int arg){
        return this;
    }
    public JPanel getContent(){
        if (contentPanel == null) {
            try {
                throw new NullPointerException("ContentPanel is not implemented.");
            }catch (Exception e){
                e.printStackTrace();
            }
            contentPanel = new JPanel();
        }

        return contentPanel;
    }

    public void afterDraw(){

    }


}
