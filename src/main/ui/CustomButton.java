package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.function.Function;

//this is the main screen options buttons
public class CustomButton extends JButton implements MouseListener {
    private LayerPlane plane;

    private String buttonText;

    public CustomButton(String text, String iconLocation, LayerPlane p) {
        super(text);
        buttonText = text;
        plane = p;
        ImageIcon givenIcon = new ImageIcon(new ImageIcon(iconLocation)
                .getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        this.setBounds(0, 0, 220, 30);
        this.setFocusable(false);
        this.setFocusPainted(false);
        this.setIcon(givenIcon);
        this.setHorizontalTextPosition(JButton.CENTER);
        this.setVerticalTextPosition(JButton.BOTTOM);
        this.setFont(new Font("Comic Sans", Font.BOLD, 10));
        this.setIconTextGap(2);
        this.setForeground(new Color(200, 200, 200));
        this.setBackground(new Color(102, 102, 102));
//        this.setBorderPainted(false);
        addMouseListener(this);
        this.setBorder(new TextBubbleBorder(Color.BLACK, 2, 30, 0));
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        plane.setOptions(buttonText);
    }

    public void mousePressed(MouseEvent e) {

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

    //MODIFIES: this
    //EFFECTS: sets error message onto button.
    public void setError() {
        System.out.println("ran this");
        this.setText("loading didn't work");
        this.setForeground(new Color(255, 0, 0));
//        this.repaint();
//        this.revalidate();
        System.out.println("here");
    }
}
