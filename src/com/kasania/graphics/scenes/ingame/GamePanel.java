package com.kasania.graphics.scenes.ingame;

import com.kasania.core.game.logic.WordTypeGame;
import com.kasania.core.game.settings.GameSettings;
import com.kasania.graphics.MainFrame;
import com.kasania.graphics.scenes.ScenePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.function.BiConsumer;

public class GamePanel extends ScenePanel {

    private JButton backToStartButton;

    private WordTypeGame gameInstance;

    private JPanel functionPanel;

    private GameCanvas gameCanvas;
    private GameScorePanel gameScorePanel;
    private GameLifePanel gameLifePanel;

    private JTextField answerInput;
    public GamePanel(){
        initializeComponents();
        makeView();
    }

    private void initializeComponents(){
        contentPanel = new JPanel();
        answerInput = new JTextField();
        functionPanel = new JPanel(new BorderLayout(5,5));
        gameLifePanel = new GameLifePanel();
        gameCanvas = new GameCanvas();
        gameScorePanel = new GameScorePanel();

        int answerInputFontSize = GameSettings.getInstance().getSettingValue(GameSettings.Items.WINDOW_WIDTH)/30;
        answerInput.setFont(new Font("Serif",Font.PLAIN,answerInputFontSize));

        contentPanel.setLayout(new BorderLayout(5,5));
        answerInput.addActionListener(this::inputAnswer);

        backToStartButton = new JButton("Give Up");
        backToStartButton.addActionListener((e)->{
            gameInstance.stopGame();
            sceneChange.accept(Scene.MAIN_MENU,0);
        });

    }

    private void makeView(){

        JPanel functionCenterWrapper = new JPanel(new BorderLayout(5,5));
        functionCenterWrapper.add(gameLifePanel.getContent(),BorderLayout.NORTH);

        functionPanel.add(gameScorePanel.getContents(),BorderLayout.NORTH);

        functionPanel.add(functionCenterWrapper,BorderLayout.CENTER);

        functionPanel.add(backToStartButton,BorderLayout.SOUTH);

        contentPanel.add(gameCanvas.getContent(),BorderLayout.CENTER);
        contentPanel.add(functionPanel,BorderLayout.EAST);
        contentPanel.add(answerInput,BorderLayout.SOUTH);

    }

    private void inputAnswer(ActionEvent unused) {
        gameInstance.checkAnswer(answerInput.getText());
        answerInput.setText("");
    }

    private String onGameOver(long score){
        String ret = MainFrame.requestInputDialog("점수 : " + score + "이름을 입력하세요.", "게임 종료");
        sceneChange.accept(Scene.MAIN_MENU,0);
        return ret;
    }

    @Override
    public ScenePanel prepareScene(int difficulty) {

        gameInstance = WordTypeGame.newGame(difficulty);

        gameInstance.addWordUpdater(gameCanvas::drawWords)
                .addScoreUpdater(gameScorePanel::setScores)
                .addLifeUpdater(gameLifePanel::updateLifePanel)
                .addGameOver(this::onGameOver);

        answerInput.setText("");

        return this;
    }

    @Override
    public void afterDraw() {
        gameCanvas.prepareGraphics();

        gameLifePanel.prepareGraphics();

        MainFrame.requestConfirmDialog("게임을 시작합니다","게임 시작");

        long start =  System.currentTimeMillis();

        gameInstance.startGame();
        long end =  System.currentTimeMillis();
        System.out.println(end-start);
        answerInput.requestFocus();

    }

    @Override
    public JPanel getContent() {
        return contentPanel;
    }

}
