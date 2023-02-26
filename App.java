import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.io.File;


public class App extends JFrame implements ActionListener, MouseListener
{
    private Board board;
    private HeadPanel headPanel;
    private AppMenuBar appMenuBar;
    private int height;
    private int width; 
    private int mines;

    private File scoreFile;
    private FileHandler fileHandler;
    private String[] scores;

    public static void main(String[] args) 
    {
        App app = new App();
        app.newGame();
    }

    public String[] getScores()
    {
        return this.scores;
    }

    public void newGame()
    {       
        this.height = 9;
        this.width = 9;
        this.mines = 10;

        this.fileManagement();

        this.headPanel = new HeadPanel(this.width, this.mines);
        this.board = new Board(this.width, this.height, this.mines);

        this.headPanel.getRestartJButton().addActionListener(this);
        this.board.addActionListenerToCells(this);
        this.board.addMouseListenerToCells(this);

        this.add(this.headPanel, BorderLayout.PAGE_START);
        this.add(this.board, BorderLayout.CENTER);

        this.appMenuBar = new AppMenuBar(this);
        this.setJMenuBar(this.appMenuBar);
        
        this.setSize(this.width*40 + 14, this.height*40 + 140);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
        this.setTitle("Minesweeper");
    }
    private void fileManagement()
    {
        scoreFile = new File("score.txt");
        fileHandler = new FileHandler(scoreFile);
        scores = fileHandler.getScores();
    }
    public void newGame(int difficulty)
    {
        if(difficulty == 1)
        {
            this.height = 9;
            this.width = 9;
            this.mines = 10;
        }
        else
        {
            if(difficulty == 2)
            {
                this.height = 16;
                this.width = 16;
                this.mines = 30;
            }
            else
            {
                if(difficulty == 3)
                {
                    this.height = 16;
                    this.width = 32;
                    this.mines = 50;
                }
            }
        }
        this.headPanel.setEnabled(false);
        this.remove(this.headPanel);
        this.headPanel = new HeadPanel(this.width, this.mines);
        this.headPanel.getRestartJButton().addActionListener(this);

        this.board.setEnabled(false);
        this.remove(this.board);
        this.board = new Board(this.width, this.height, this.mines);
        this.board.addActionListenerToCells(this);
        this.board.addMouseListenerToCells(this);

        this.setSize(this.width*40 + 14, this.height*40 + 140);
        this.add(this.headPanel, BorderLayout.PAGE_START);
        this.add(this.board, BorderLayout.CENTER);
    }
    private void restart()
    {
        this.board.restart();
        this.headPanel.restart();
    }
    private void gameOver(boolean win)
    {
        this.board.gameOver(win);
        this.headPanel.gameOver(win);
        if(win)
        {
            this.checkScore();
            this.saveScores();
        }
    }
    private void checkScore()
    {
        String scoreString = this.headPanel.getTimerJLabel().getText();
        int newScoreInt = scoreToInt(scoreString);
        int scoreInt;
        switch (this.mines) 
        {
            case 10:
                if(!scores[0].equals("Null"))
                {
                    scoreInt = scoreToInt(scores[0]);
                    if(newScoreInt < scoreInt)
                        this.scores[0] = intToScore(newScoreInt);
                }
                else
                    this.scores[0] = intToScore(newScoreInt);
                break;
            case 30:
                if(!scores[1].equals("Null"))
                {
                    scoreInt = scoreToInt(scores[1]);
                    if(newScoreInt < scoreInt)
                        this.scores[1] = intToScore(newScoreInt);
                }
                else
                    this.scores[1] = intToScore(newScoreInt);
                break;  
            case 50:
                if(!scores[2].equals("Null"))
                {
                    scoreInt = scoreToInt(scores[2]);
                    if(newScoreInt < scoreInt)
                        this.scores[2] = intToScore(newScoreInt);
                }
                else
                    this.scores[2] = intToScore(newScoreInt);
                break;
        }
    }
    private int scoreToInt(String scoreString)
    {
        String[] tmp = scoreString.split(":");
        int scoreInt = Integer.parseInt(tmp[0])*60+Integer.parseInt(tmp[1]);
        return scoreInt;
    }
    private String intToScore(int score)
    {
        return (score/60+":"+score%60);
    }
    private void saveScores()
    {
        fileHandler.setScores(this.scores);
        fileHandler.saveScores();
    }
    

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        Object source = e.getSource();
        if(source == this.headPanel.getRestartJButton())
        {
            this.restart();
        }
        if(source.getClass() == Cell.class)
        {
            Cell cell = (Cell) e.getSource();
            if(cell.IsBomb() == true && this.headPanel.getLivesJLabel().getText() == "0")
                this.gameOver(false);
            else
            {
                if(cell.IsBomb() == true)
                {
                    if(this.headPanel.getLivesJLabel().getText() == "2")
                        this.headPanel.getLivesJLabel().setText("1");
                    else
                        if(this.headPanel.getLivesJLabel().getText() == "1")
                            this.headPanel.getLivesJLabel().setText("0");
                }
                setMinesLabel();
                int flags = this.board.getNumberOfFlags();
                int disabled = this.board.getNumberOfDisabledCells();
                int lives = Integer.parseInt(this.headPanel.getLivesJLabel().getText());
                if(flags + (this.height*this.width-disabled) + (2-lives) == this.mines)
                    this.gameOver(true);
            }    
        }
    }

    private void setMinesLabel()
    {
        int minesFound = this.board.getNumberOfFlags() + (2 - Integer.parseInt(this.headPanel.getLivesJLabel().getText()));
        int minesRemaining = this.mines - minesFound;
        if(minesRemaining < 0)
            this.headPanel.getMinesJLabel().setText("0");
        else
            this.headPanel.getMinesJLabel().setText(String.valueOf(minesRemaining));
    }

    @Override
    public void mouseClicked(MouseEvent e) 
    {
        if(e.getButton() == 3)
        {
            setMinesLabel();
        }
    }
    @Override
    public void mousePressed(MouseEvent e) 
    {   
    }
    @Override
    public void mouseReleased(MouseEvent e) 
    {    
    }
    @Override
    public void mouseEntered(MouseEvent e) 
    {   
    }
    @Override
    public void mouseExited(MouseEvent e) 
    {  
    }
}