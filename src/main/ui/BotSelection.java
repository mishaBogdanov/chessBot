package ui;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;

//this is the bot selection tab
public class BotSelection extends JPanel {
    char currentColorSelected;
    int currentDifficultySelected;
    LayerPlane lp;

    public BotSelection(LayerPlane lp) {
        this.lp = lp;
        this.setLayout(new GridLayout(3, 1, 0, 5));
        this.setPreferredSize(new Dimension(250, 500));
        this.setBackground(new Color(45, 45, 45));
        AbstractBorder brdrLeft = new TextBubbleBorder(Color.BLACK, 2, 30, 0);
        this.setBorder(brdrLeft);
        JPanel row1 = new JPanel();
        row1.setLayout(new GridLayout(1, 2, 2, 0));
        row1.add(new PlayWhiteButton(this, 'W'));
        row1.add(new PlayWhiteButton(this, 'B'));
        row1.setBackground(new Color(45, 45, 45));
        this.add(row1);
        JPanel row2 = new JPanel();
        row2.setPreferredSize(new Dimension(200, 800));
        row2.setLayout(new GridLayout(3, 2, 2, 0));
        row2.add(new ChooseDifficultyButton(this, 1));
        row2.add(new ChooseDifficultyButton(this, 2));
        row2.add(new ChooseDifficultyButton(this, 3));
        row2.add(new ChooseDifficultyButton(this, 4));
        row2.add(new ChooseDifficultyButton(this, 5));
        row2.setBackground(new Color(45, 45, 45));
        this.add(row2);
        this.add(new PlayBotButton(this));

    }


    //EFFECTS: launches the bot game
    public void launchBot() {
        lp.setupBotPlay(currentDifficultySelected, currentColorSelected);
    }

    public void setCurrentColorSelected(char c) {
        currentColorSelected = c;
    }

    public void setCurrentDifficultySelected(int c) {
        currentDifficultySelected = c;
    }
}
