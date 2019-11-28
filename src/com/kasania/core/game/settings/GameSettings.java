package com.kasania.core.game.settings;

import java.util.ArrayList;
import java.util.HashMap;

public class GameSettings {

    public enum Items {
        FRAME_RATE, WINDOW_MAGNIFICATION, WINDOW_WIDTH, WINDOW_HEIGHT
    }

    private static GameSettings instance;

    private HashMap<GameSettings.Items, Integer> settings;

    private GameSettings(){
        settings = new HashMap<>();

    }

    public static GameSettings getInstance(){
        if(instance == null){
            instance = new GameSettings();
        }

        return instance;
    }

    public void setDefaultSettings(ArrayList<String> rawSettings){

        settings.clear();

        rawSettings.forEach(str->{
            String[] settingStr = str.split(":");
            settings.put(GameSettings.Items.valueOf(settingStr[0]),Integer.parseInt(settingStr[1]));
        });

    }

    public int getSettingValue(GameSettings.Items item){
        return settings.get(item);
    }

    public void setSettingValue(GameSettings.Items item, int value){
        settings.replace(item,value);
    }

    public ArrayList<String> getAllAttribute(){
        ArrayList<String> settingArrayList = new ArrayList<>();
        settings.forEach((items, integer) -> {
            settingArrayList.add(items.toString() + ":" + integer);
        });

        return settingArrayList;
    }

}
