package ui;

import model.ChessGame;
import model.ListOfGames;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//this is the list of previous games
public class MyCustomList extends JList {
    ListOfGames givenList;
    ChessViewGame view;
    MyCustomList forFuture;

    public MyCustomList(ListOfGames list, ChessViewGame game) {
        super(ListsClass.giveList(list, list.getGames().size()));
        forFuture = this;
        view = game;
        givenList = list;
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setBackground(Color.black);

        this.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                view.setGame(givenList.getGames().get(forFuture.getSelectedIndex()));

            }
        });
    }



}
