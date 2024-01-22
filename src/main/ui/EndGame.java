package ui;

import model.ListOfGames;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;

//this is the end game pannel
public class EndGame extends JPanel {
    public EndGame(ChessPvPMenu game) {
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(340, 500));
        this.setBackground(new Color(45, 45, 45));
        AbstractBorder brdrLeft = new TextBubbleBorder(Color.BLACK, 2, 30, 0);
        this.setBorder(brdrLeft);
        this.add(new WhiteWinButton(game));
        this.add(new BlackWinButton(game));
        this.add(new DrawButton(game));

    }
}
