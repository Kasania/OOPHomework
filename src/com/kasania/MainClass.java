package com.kasania;

import com.kasania.core.FileManager;
import com.kasania.core.game.settings.GameSettings;
import com.kasania.graphics.MainFrame;

public class MainClass {
    public static void main(String[] args) {
        GameSettings.getInstance().setDefaultSettings(FileManager.getInstance().getSettingList());
        new MainFrame();

    }

}
