package com.kasania.graphics.scenes.setting;

import com.kasania.core.game.settings.GameSettings;

import javax.swing.*;
import java.awt.*;

public class FrameRateSettingPanel {

    private JPanel content;

    private JSlider frameRateSlider;

    private int currentFrameRate;
    private boolean valueChanged;

    FrameRateSettingPanel(){

        valueChanged = false;
        content = new JPanel(new BorderLayout(15,0));
        content.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));

        currentFrameRate = GameSettings.getInstance().getSettingValue(GameSettings.Items.FRAME_RATE);

        JLabel frameRateLabel = new JLabel("Frame rate : "+ currentFrameRate);

        Font font = new Font("Serif",Font.PLAIN,
                GameSettings.getInstance().getSettingValue(GameSettings.Items.WINDOW_HEIGHT)/35);
        frameRateLabel.setFont(font);

        frameRateSlider = new JSlider(JSlider.HORIZONTAL,30,240, currentFrameRate);
        frameRateSlider.setMajorTickSpacing(15);
        frameRateSlider.setMinorTickSpacing(5);
        frameRateSlider.setPaintTicks(true);
        frameRateSlider.setPaintLabels(true);
        frameRateSlider.addChangeListener(e -> {
            valueChanged = true;
            currentFrameRate = frameRateSlider.getValue();
            frameRateLabel.setText("Frame rate : "+ currentFrameRate);
        });


        content.add(frameRateSlider,BorderLayout.CENTER);
        content.add(frameRateLabel,BorderLayout.EAST);

    }

    boolean applyChange(){
        if(valueChanged){
            GameSettings.getInstance().setSettingValue(GameSettings.Items.FRAME_RATE, currentFrameRate);
            return true;
        }
        return false;
    }

    JPanel getContents(){
        return content;
    }

}
