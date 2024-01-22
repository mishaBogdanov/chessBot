package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.Function;

//this is the previous move button
public class PrevButton extends JButton implements MouseListener {
    ChessViewGame currentGame;

    @SuppressWarnings("methodlength")
    public PrevButton(ChessViewGame game) {
        super("prev move");
        this.currentGame = game;
        ImageIcon givenIcon = new ImageIcon(new ImageIcon("./src/main/ui/things/left-arrow.png")
                .getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        this.setBounds(0, 0, 220, 30);
        this.setPreferredSize(new Dimension(120, 50));
        this.setFocusable(false);
        this.setFocusPainted(false);
        this.setIcon(givenIcon);
        this.setHorizontalTextPosition(JButton.CENTER);
        this.setVerticalTextPosition(JButton.BOTTOM);
        this.setFont(new Font("Comic Sans", Font.BOLD, 10));
        this.setIconTextGap(-15);
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
        currentGame.prevMove();
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
    //EFFECTS: brightens button
    @Override
    public void mouseExited(MouseEvent e) {

        if (e.getSource() == this) {
            this.setBackground(new Color(102, 102, 102));
        }

    }
}
