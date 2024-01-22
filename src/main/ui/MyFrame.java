package ui;

import model.EventLog;
import model.ListOfGames;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import model.Event.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;

//this is the main window of the game
public class MyFrame extends JFrame implements WindowListener,
        WindowFocusListener,
        WindowStateListener {

    public MyFrame() {


        this.setSize(1170, 580);
        this.setTitle("chess");
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.getContentPane().setBackground(new Color(36, 36, 36));
        LayerPlane lp = new LayerPlane();
        this.add(new LayerPlane());
        addWindowListener(this);
        addWindowFocusListener(this);
        addWindowStateListener(this);

        this.setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    //EFFECTS: prints event log when window is closed
    @Override
    public void windowClosing(WindowEvent e) {
        for (model.Event event : EventLog.getInstance()) {
            System.out.println();
            System.out.println(event.toString());
            System.out.println();
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void windowGainedFocus(WindowEvent e) {

    }

    @Override
    public void windowLostFocus(WindowEvent e) {

    }

    @Override
    public void windowStateChanged(WindowEvent e) {

    }
}
