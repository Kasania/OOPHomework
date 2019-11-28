package com.kasania.graphics.scenes.wordmanager;

import com.kasania.core.game.settings.GameSettings;

import javax.swing.*;
import java.awt.*;
import java.util.function.Consumer;

public class WordElementPanel {
    private JPanel content;
    private String string;
    private Consumer<String> onRemove;

    WordElementPanel(String str){
        int wordLabelFontSize = GameSettings.getInstance().getSettingValue(GameSettings.Items.WINDOW_WIDTH)/25;
        int panelWidth = (int) (GameSettings.getInstance().getSettingValue(GameSettings.Items.WINDOW_WIDTH)*0.8);
        int panelHeight = GameSettings.getInstance().getSettingValue(GameSettings.Items.WINDOW_WIDTH)/18;

        string = str;
        content = new JPanel(new BorderLayout(10,10));

        content.setPreferredSize(new Dimension(panelWidth,panelHeight));
        content.setMaximumSize(new Dimension(panelWidth,panelHeight));
        content.setMinimumSize(new Dimension(panelWidth,panelHeight));

        content.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));

        JLabel wordLabel = new JLabel(str);
        wordLabel.setFont(new Font("Serif", Font.PLAIN, wordLabelFontSize));
        wordLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton removeButton = new JButton("Remove");
        removeButton.setFont(new Font("Serif",Font.PLAIN,wordLabelFontSize));
        removeButton.addActionListener((e)->onRemove.accept(string));

        content.add(wordLabel,BorderLayout.CENTER);
        content.add(removeButton, BorderLayout.EAST);

    }

    void setOnRemove(Consumer<String> remove){
        onRemove = remove;
    }

    JPanel getContent(){
        return content;
    }
}
