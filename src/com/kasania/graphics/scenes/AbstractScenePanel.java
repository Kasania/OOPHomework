package com.kasania.graphics.scenes;

import javax.swing.*;
import java.util.function.BiConsumer;

public abstract class AbstractScenePanel {

    public enum Scene {
        MAIN_MENU, WORD_MANAGER, SETTING,
        PLAYER_MODE_SELECT,
        SINGLE_GAME_MODE_SELECT,
        MULTI_GAME_MODE_SELECT,
        WORD_TYPING_LEVEL_SELECT, LYRICS_TYPING_STAGE_SELECT,
        WORD_TYPING_GAME, LYRICS_TYPING_GAME,

    }

    protected JPanel contentPanel;
    protected BiConsumer<Scene,Integer> sceneChange;

    public void addSceneChanger(BiConsumer<Scene, Integer> sceneChanger){
        this.sceneChange = sceneChanger;
    }
    public AbstractScenePanel prepareScene(int arg){
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
