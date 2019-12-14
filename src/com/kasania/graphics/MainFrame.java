package com.kasania.graphics;

import com.kasania.core.FileManager;
import com.kasania.core.game.settings.GameSettings;
import com.kasania.graphics.scenes.LevelSelectPanel;
import com.kasania.graphics.scenes.MainMenuPanel;
import com.kasania.graphics.scenes.PlayerModeSelectPanel;
import com.kasania.graphics.scenes.AbstractScenePanel;
import com.kasania.graphics.scenes.ingame.GamePanel;
import com.kasania.graphics.scenes.setting.SettingPanel;
import com.kasania.graphics.scenes.wordmanager.WordManagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.EnumMap;

public class MainFrame {

    private static JFrame mainFrame;

    private static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
    private static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;

    private EnumMap<AbstractScenePanel.Scene, AbstractScenePanel> contents;


    public MainFrame(){

        initializeComponents();
        makeFrame();

    }

    private void initializeComponents(){
        mainFrame = new JFrame();

        contents = new EnumMap<>(AbstractScenePanel.Scene.class);

        MainMenuPanel mainMenuPanel = new MainMenuPanel();
        mainMenuPanel.addSceneChanger(this::changeScene);
        mainMenuPanel.onExit(this::onDestroy);
        contents.put(AbstractScenePanel.Scene.MAIN_MENU, mainMenuPanel);

        PlayerModeSelectPanel playerModeSelectPanel = new PlayerModeSelectPanel();
        playerModeSelectPanel.addSceneChanger(this::changeScene);
        contents.put(AbstractScenePanel.Scene.PLAYER_MODE_SELECT,playerModeSelectPanel);

        LevelSelectPanel levelSelectPanel = new LevelSelectPanel();
        levelSelectPanel.addSceneChanger(this::changeScene);
        contents.put(AbstractScenePanel.Scene.WORD_TYPING_LEVEL_SELECT,levelSelectPanel);

        SettingPanel settingPanel = new SettingPanel();
        settingPanel.addSceneChanger(this::changeScene);
        settingPanel.addReloadFrame(this::restart);
        contents.put(AbstractScenePanel.Scene.SETTING,settingPanel);

        WordManagePanel wordInputPanel = new WordManagePanel();
        wordInputPanel.addSceneChanger(this::changeScene);
        contents.put(AbstractScenePanel.Scene.WORD_MANAGER,wordInputPanel);

        GamePanel gamePanel = new GamePanel();
        gamePanel.addSceneChanger(this::changeScene);
        contents.put(AbstractScenePanel.Scene.WORD_TYPING_GAME,gamePanel);

    }

    private void changeScene(AbstractScenePanel.Scene scene, int arg){

        mainFrame.getContentPane().removeAll();
        AbstractScenePanel next = contents.get(scene).prepareScene(arg);
        JPanel nextPanel = next.getContent();
        mainFrame.getContentPane().add(nextPanel);
        mainFrame.getContentPane().revalidate();
        mainFrame.getContentPane().repaint();

        contents.get(scene).afterDraw();
    }

    private void makeFrame(){

        int WINDOW_MAG = GameSettings.getInstance().getSettingValue(GameSettings.Items.WINDOW_MAGNIFICATION);
        int WINDOW_WIDTH = 16* WINDOW_MAG;
        int WINDOW_HEIGHT = 9* WINDOW_MAG;

        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            onDestroy();
            }
        });

        mainFrame.setTitle("Word Crasher");

        mainFrame.setBounds((SCREEN_WIDTH - WINDOW_WIDTH)/2,(SCREEN_HEIGHT - WINDOW_HEIGHT)/2-16,
                WINDOW_WIDTH, WINDOW_HEIGHT);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);

        changeScene(AbstractScenePanel.Scene.MAIN_MENU,0);
    }

    private void onDestroy(){
        if(requestQuestionDialog("종료하시겠습니까?","종료")){
            mainFrame.dispose();

            saveDataFiles();
            System.exit(0);

        }else{
            System.out.println("Close Canceled");
        }
    }

    private void restart(){
        saveDataFiles();

        mainFrame.dispose();

        FileManager.getInstance().readFiles();
        GameSettings.getInstance().setDefaultSettings(FileManager.getInstance().getSettingList());

        mainFrame = null;

        initializeComponents();
        makeFrame();
    }

    private void saveDataFiles(){

        int WINDOW_MAG = GameSettings.getInstance().getSettingValue(GameSettings.Items.WINDOW_MAGNIFICATION);
        int WINDOW_WIDTH = 16* WINDOW_MAG;
        int WINDOW_HEIGHT = 9* WINDOW_MAG;

        GameSettings.getInstance().setSettingValue(GameSettings.Items.WINDOW_WIDTH,WINDOW_WIDTH);
        GameSettings.getInstance().setSettingValue(GameSettings.Items.WINDOW_HEIGHT,WINDOW_HEIGHT);

        FileManager.getInstance().saveSettingList();
        FileManager.getInstance().saveWordList();
        FileManager.getInstance().saveScoreList();
    }

    public static boolean requestQuestionDialog(String message, String title){
        return JOptionPane.showConfirmDialog(mainFrame,message,title,JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
    }

    public static void requestConfirmDialog(String message, String title){
        JOptionPane.showConfirmDialog(mainFrame, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
    }

    public static String requestInputDialog(String message, String title){
        return JOptionPane.showInputDialog(mainFrame, message, title,JOptionPane.INFORMATION_MESSAGE);
    }

}
