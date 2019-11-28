package com.kasania.graphics.scenes;

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
    public abstract ScenePanel prepareScene(int arg);
    public abstract JPanel getContent();

    public void afterDraw(){

    }


}
