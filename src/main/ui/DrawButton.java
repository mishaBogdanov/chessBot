package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.Function;

//this is the draw button
public class DrawButton extends JButton implements MouseListener {
    ChessPvPMenu menu;

    public DrawButton(ChessPvPMenu menu) {
        super("draw");
        this.menu = menu;
        ImageIcon givenIcon = new ImageIcon(new ImageIcon("./src/main/ui/things/whiteFlag.png")
                .getImage().getScaledInstance(70, 80, Image.SCALE_DEFAULT));
        this.setBounds(0, 0, 220, 90);
        this.setPreferredSize(new Dimension(120, 90));
//        this.setFocusable(false);
//        this.setFocusPainted(false);
//        this.setIcon(givenIcon);
//        this.setHorizontalTextPosition(JButton.CENTER);
//        this.setVerticalTextPosition(JButton.BOTTOM);
//        this.setFont(new Font("Comic Sans", Font.BOLD, 10));
//        this.setIconTextGap(-12);
        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

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
        menu.draw();
    }

    public void mouseReleased(MouseEvent e) {
    }

    //MODIFIES: this
    //EFFECTS: darkens button when mouse entered
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