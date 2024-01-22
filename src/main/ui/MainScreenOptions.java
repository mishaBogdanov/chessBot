package ui;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.util.function.Function;

//this is the options you see on the main screen
public class MainScreenOptions extends JPanel {
    LayerPlane lp;
    CustomButton loadGames;
    CustomButton saveGames;

    public MainScreenOptions(LayerPlane l) {
        lp = l;
        this.setLayout(new GridLayout(5, 1, 0, 5));
        this.setPreferredSize(new Dimension(250, 500));
        this.setBackground(new Color(45, 45, 45));
        AbstractBorder brdrLeft = new TextBubbleBorder(Color.BLACK, 2, 30, 0);

        JButton playAgainstFriend =
                new CustomButton("play against friend", "./src/main/ui/things/smile.png", lp);


        JButton playAgainstBot =
                new CustomButton("play against bot", "./src/main/ui/things/img_1.png", lp);
        JButton viewPrevGames =
                new CustomButton("view games",
                        "./src/main/ui/things/bookIcon.png", lp);
        loadGames = new CustomButton("import games",
                "./src/main/ui/things/download.png", lp);
        saveGames = new CustomButton("save games",
                "./src/main/ui/things/diskette.png", lp);


        this.setBorder(brdrLeft);
        this.add(playAgainstFriend);
        this.add(playAgainstBot);
        this.add(viewPrevGames);

        this.add(loadGames);
        this.add(saveGames);
//        this.add(this);
    }

    //EFFECTS: sets error if failed to load
    public void getGetGamesError() {
        loadGames.setError();
    }

}
