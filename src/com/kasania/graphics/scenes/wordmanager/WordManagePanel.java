package com.kasania.graphics.scenes.wordmanager;

import com.kasania.core.FileManager;
import com.kasania.core.game.settings.GameSettings;
import com.kasania.graphics.scenes.AbstractScenePanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class WordManagePanel extends AbstractScenePanel {

    private JPanel wordAdderPanel;
    private JTextField wordAdderTextField;

    private JScrollPane wordListScroll;
    private JPanel wordListWrapperPanel;
    private ArrayList<WordElementPanel> wordElementPanelList;

    private JButton backToStartButton;

    private ArrayList<String> wordList;

    public WordManagePanel(){
        initializeComponents();
        makeView();
    }

    private void initializeComponents(){

        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(5,5));

        makeWordInputPanel();
        makeWordListPanel();

        backToStartButton = new JButton("Save & Return");
        backToStartButton.setFont(new Font("Serif",Font.PLAIN,50));

        backToStartButton.addActionListener((e)->{
            FileManager.getInstance().replaceWordList(wordList);
            sceneChange.accept(Scene.MAIN_MENU,0);
        });

    }

    private void makeWordInputPanel(){
        int textFieldFontSize = GameSettings.getInstance().getSettingValue(GameSettings.Items.WINDOW_WIDTH)/25;
        wordAdderPanel = new JPanel(new BorderLayout(5,0));
        JLabel LAB_wordAdder = new JLabel("Word : ");
        LAB_wordAdder.setFont(new Font("Serif",Font.PLAIN,textFieldFontSize));
        wordAdderTextField = new JTextField();
        wordAdderTextField.setFont(new Font("Serif",Font.PLAIN,textFieldFontSize));

        wordAdderTextField.addActionListener((e)->addWord(wordAdderTextField.getText()));

        JButton BTN_wordAdder = new JButton("Add");
        BTN_wordAdder.setFont(new Font("Serif",Font.PLAIN,textFieldFontSize));
        BTN_wordAdder.addActionListener((e)->addWord(wordAdderTextField.getText()));

        wordAdderPanel.add(LAB_wordAdder,BorderLayout.WEST);
        wordAdderPanel.add(wordAdderTextField,BorderLayout.CENTER);
        wordAdderPanel.add(BTN_wordAdder,BorderLayout.EAST);
    }

    private void makeWordListPanel(){
        wordList = FileManager.getInstance().getWordList();
        wordListScroll = new JScrollPane();
        wordListScroll.getVerticalScrollBar().setUnitIncrement(16);
        wordListWrapperPanel = new JPanel();
        wordListWrapperPanel.setLayout(new BoxLayout(wordListWrapperPanel,BoxLayout.Y_AXIS));

        wordElementPanelList = new ArrayList<>();



        for (String s : wordList) {
            WordElementPanel wordElementPanel = new WordElementPanel(s);
            wordElementPanel.setOnRemove(this::removeWord);
            addWordElementPanelToView(wordElementPanel);
        }


        wordListScroll.getViewport().setView(wordListWrapperPanel);

        contentPanel.setBorder(BorderFactory.createLineBorder(contentPanel.getBackground(),10));

    }

    private void makeView(){

        contentPanel.add(wordAdderPanel,BorderLayout.NORTH);
        contentPanel.add(wordListScroll,BorderLayout.CENTER);
        contentPanel.add(backToStartButton,BorderLayout.SOUTH);
    }

    private void addWord(String word){
        if(word.contentEquals("")){
            return;
        }

        wordAdderTextField.setText("");
        WordElementPanel wordElementPanel = new WordElementPanel(word);
        wordList.add(word);
        wordElementPanel.setOnRemove(this::removeWord);

        addWordElementPanelToView(wordElementPanel);

        wordListWrapperPanel.revalidate();
        wordListWrapperPanel.repaint();
    }

    private void removeWord(String word){
        int idx = wordList.indexOf(word);
        wordList.remove(idx);
        wordListWrapperPanel.remove(wordElementPanelList.get(idx).getContent());
        wordElementPanelList.remove(idx);

        wordListWrapperPanel.revalidate();
        wordListWrapperPanel.repaint();
    }

    private void addWordElementPanelToView(WordElementPanel wordElementPanel){
        wordElementPanelList.add(wordElementPanel);
        wordListWrapperPanel.add(wordElementPanel.getContent(),0);
    }

    @Override
    public AbstractScenePanel prepareScene(int arg) {

        wordList = FileManager.getInstance().getWordList();

        return this;
    }

}
