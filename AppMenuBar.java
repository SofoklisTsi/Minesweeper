import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.*;

public class AppMenuBar extends JMenuBar implements ActionListener
{
    private App app;
    private JMenu menu;
    private JMenu info;
    private JMenu changeDifficulty;
    private JMenuItem newGame;
    private JMenuItem easy;
    private JMenuItem medium;
    private JMenuItem hard;
    private JMenuItem exit;
    private JMenuItem score;
    private JMenuItem about;

    public AppMenuBar(App app)
    {
        this.app = app;
        this.initMenu();
    }

    private void initMenu()
    {
        menu = new JMenu("Game");
        info = new JMenu("Info");
        changeDifficulty = new JMenu("Change Difficulty");
        newGame = new JMenuItem("New Game");
        easy = new JMenuItem("Easy (9x9)");
        medium = new JMenuItem("Medium (16x16)");
        hard = new JMenuItem("Hard (16x32)");
        exit = new JMenuItem("Exit");
        score = new JMenuItem("Score");
        about = new JMenuItem("About");
        newGame.addActionListener(this);
        easy.addActionListener(this);
        medium.addActionListener(this);
        hard.addActionListener(this);
        exit.addActionListener(this);
        score.addActionListener(this);
        about.addActionListener(this);
        changeDifficulty.add(easy);
        changeDifficulty.add(medium);
        changeDifficulty.add(hard);
        menu.add(newGame);
        menu.add(changeDifficulty);
        menu.add(exit);
        info.add(score);
        info.add(about);
        this.add(menu);
        this.add(info);
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        Object source = e.getSource();
        if(source == this.newGame)
            app.newGame(0);
        if(source == this.easy)
            app.newGame(1);
        if(source == this.medium)
            app.newGame(2);
        if(source == this.hard)
            app.newGame(3);
        if(source == this.exit)
            app.dispatchEvent(new WindowEvent(app, WindowEvent.WINDOW_CLOSING));
        if(source == this.score)
            this.showScores();
        if(source == this.about)
            this.about();
    }

    private void showScores()
    {
        JOptionPane.showMessageDialog(this, "Easy: "+app.getScores()[0]+"\nMedium: "+app.getScores()[1]+
                        "\nHard: "+app.getScores()[2], "Scores", JOptionPane.INFORMATION_MESSAGE);   
    }
    private void about()
    {
        JOptionPane.showMessageDialog(this, "Minesweeper Project by\n"+
                        "Tsiakalos Sofoklis Evangelos\nTP4795", "About", JOptionPane.INFORMATION_MESSAGE);
    }    
}
