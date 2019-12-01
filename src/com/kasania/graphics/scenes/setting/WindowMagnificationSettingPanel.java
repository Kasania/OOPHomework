package com.kasania.graphics.scenes.setting;

import com.kasania.core.game.settings.GameSettings;

import javax.swing.*;
import java.awt.*;

public class WindowMagnificationSettingPanel {

    private JPanel content;

    private JSlider magnificationSlider;

    private int currentMagnification;
    private boolean valueChanged;

    WindowMagnificationSettingPanel(){
        valueChanged = false;
        content = new JPanel(new BorderLayout(15,0));
        content.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
        currentMagnification = GameSettings.getInstance().getSettingValue(GameSettings.Items.WINDOW_MAGNIFICATION);

        JPanel screenSizeWrapper = new JPanel(new GridLayout(3,1));

        JLabel magnificationLabel = new JLabel("Size : "+currentMagnification + "%");

        JLabel widthLabel = new JLabel("Width : "+ 16 * currentMagnification);
        JLabel heightLabel = new JLabel("Height : " + 9 * currentMagnification);

        Font font = new Font("Serif",Font.PLAIN,
                GameSettings.getInstance().getSettingValue(GameSettings.Items.WINDOW_HEIGHT)/35);
        magnificationLabel.setFont(font);
        widthLabel.setFont(font);
        heightLabel.setFont(font);

        magnificationSlider = new JSlider(JSlider.HORIZONTAL,60,150,currentMagnification);
        magnificationSlider.setMajorTickSpacing(10);
        magnificationSlider.setMinorTickSpacing(5);
        magnificationSlider.setPaintTicks(true);
        magnificationSlider.setPaintLabels(true);
        magnificationSlider.addChangeListener(e -> {
            valueChanged = true;
            currentMagnification = magnificationSlider.getValue();
            magnificationLabel.setText("Size : "+currentMagnification + "%");
            widthLabel.setText("Width : " + 16 * currentMagnification);
            heightLabel.setText("Height : " + 9 * currentMagnification);
        });

        screenSizeWrapper.add(magnificationLabel);
        screenSizeWrapper.add(widthLabel);
        screenSizeWrapper.add(heightLabel);

        content.add(magnificationSlider,BorderLayout.CENTER);
        content.add(screenSizeWrapper,BorderLayout.EAST);

    }

    boolean applyChange(){
        if(valueChanged){
            GameSettings.getInstance().setSettingValue(GameSettings.Items.WINDOW_MAGNIFICATION,currentMagnification);
            return true;
        }
        return false;
    }

    JPanel getContents(){
        return content;
    }
}
