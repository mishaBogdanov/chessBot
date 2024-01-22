package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

//this is the play button in bot menu
public class PlayBotButton extends JButton implements MouseListener {
    BotSelection menu;



    public PlayBotButton(BotSelection menu) {

        super("PLAY");
        this.menu = menu;
        this.setPreferredSize(new Dimension(120, 200));
        this.setFocusable(false);
        this.setFocusPainted(false);
        this.setHorizontalTextPosition(JButton.CENTER);
        this.setVerticalTextPosition(JButton.BOTTOM);
        this.setFont(new Font("Comic Sans", Font.BOLD, 30));
        this.setForeground(new Color(200, 200, 200));
        this.setBackground(new Color(102, 102, 102));
//        this.setBorderPainted(false);
        addMouseListener(this);
        this.setBorder(new TextBubbleBorder(Color.BLACK, 2, 30, 0));
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        menu.launchBot();
    }

    public void mouseReleased(MouseEvent e) {
    }

    //MODIFIES: this
    //EFFECTS: darkens button
    @Override
    public void mouseEntered(MouseEvent e) {

        if (e.getSource() == this) {
            this.setBackground(new Color(61, 61, 61));
        }

    }

    //MODIFIES: this
    //EFFECTS: lightens button
    @Override
    public void mouseExited(MouseEvent e) {

        if (e.getSource() == this) {
            this.setBackground(new Color(102, 102, 102));
        }

    }
}