package ui;

import model.ListOfGames;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;

//this is the select game tile
public class SelectGame extends JPanel {
    public SelectGame(ListOfGames list, ChessViewGame game) {
        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(340, 500));
        this.setBackground(new Color(45, 45, 45));
        AbstractBorder brdrLeft = new TextBubbleBorder(Color.BLACK, 2, 30, 0);
        this.setBorder(brdrLeft);
        this.add(new MyCustomList(list, game));
        this.add(new PrevButton(game));
        this.add(new NextButton(game));

    }
}
