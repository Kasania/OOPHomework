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

        JPanel innerContent = new JPanel(new BorderLayout(10,10));

        string = str;
        content = new JPanel();

        innerContent.setPreferredSize(new Dimension(panelWidth,panelHeight));
        innerContent.setMaximumSize(new Dimension(panelWidth,panelHeight));
        innerContent.setMinimumSize(new Dimension(panelWidth,panelHeight));

        innerContent.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));

        JLabel wordLabel = new JLabel(str);
        wordLabel.setFont(new Font("Serif", Font.PLAIN, wordLabelFontSize));
        wordLabel.setHorizontalAlignment(JLabel.CENTER);

        JButton removeButton = new JButton("Remove");
        removeButton.setFont(new Font("Serif",Font.PLAIN,wordLabelFontSize));
        removeButton.addActionListener((e)->onRemove.accept(string));

        innerContent.add(wordLabel,BorderLayout.CENTER);
        innerContent.add(removeButton, BorderLayout.EAST);

        content.setBorder(BorderFactory.createLineBorder(content.getBackground(),1));
        content.add(innerContent);

    }

    void setOnRemove(Consumer<String> remove){
        onRemove = remove;
    }

    JPanel getContent(){
        return content;
    }
}
