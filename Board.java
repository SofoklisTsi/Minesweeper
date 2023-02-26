import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.util.Random;
import java.awt.event.*;
import java.awt.Dimension;

public class Board extends JPanel implements ActionListener, MouseListener
{
    private Cell[][] cells;
    private GridLayout gridLayout; 
    private boolean gameOverFlag;

    private int boardWidth;
    private int boardHeight;
    private int mines;

    public Board(int boardWidth, int boardHeight, int mines)
    {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.mines = mines;
        this.gameOverFlag = false;
        this.setPreferredSize(new Dimension(this.boardWidth*40, this.boardHeight*40));
        this.gridLayout = new GridLayout(this.boardHeight, this.boardWidth); 
        this.setLayout(gridLayout);
        this.initBoard();
    }

    public int getNumberOfFlags()
    {
        int flags = 0;
        for (int i = 0; i < this.boardHeight; i++) 
            for (int j = 0; j < this.boardWidth; j++) 
                if(this.cells[i][j].isFlaged() == true)
                    flags++;
        return flags;
    }  
    public int getNumberOfDisabledCells()
    {
        int disabled = 1;
        for (int i = 0; i < this.boardHeight; i++) 
            for (int j = 0; j < this.boardWidth; j++) 
                if(this.cells[i][j].isEnabled() == false)
                    disabled++;
        return disabled;
    }  

    public void restart()
    {
        for (int i = 0; i < this.boardHeight; i++) 
            for (int j = 0; j < this.boardWidth; j++) 
                this.cells[i][j].restart();
        this.gameOverFlag = false;
    }

    public void gameOver(boolean win)
    {
        gameOverFlag = true;
        if(win == false)
        {
            for (int i = 0; i < this.boardHeight; i++) 
                for (int j = 0; j < this.boardWidth; j++) 
                {
                    if(this.cells[i][j].IsBomb() && this.cells[i][j].isFlaged())
                        this.cells[i][j].setFlaged(!this.cells[i][j].isFlaged());
                    if(this.cells[i][j].IsBomb())
                        this.cells[i][j].setEnabled(false);
                    if(this.cells[i][j].isEnabled() == true)
                        this.cells[i][j].gameOver();
                }   
        }   
        else
            for (int i = 0; i < this.boardHeight; i++) 
                for (int j = 0; j < this.boardWidth; j++) 
                {
                    if(this.cells[i][j].IsBomb())
                        this.cells[i][j].setFlaged(true);
                }
    }
    public void addActionListenerToCells(ActionListener app)
    {
        for (int i = 0; i < this.boardHeight; i++) 
            for (int j = 0; j < this.boardWidth; j++) 
                this.cells[i][j].addActionListener(app);
    }
    public void addMouseListenerToCells(MouseListener app)
    {
        for (int i = 0; i < this.boardHeight; i++) 
            for (int j = 0; j < this.boardWidth; j++) 
                this.cells[i][j].addMouseListener(app);
    }
    private void initBoard()
    {
        this.cells = new Cell[this.boardHeight][this.boardWidth];
        for (int i = 0; i < this.boardHeight; i++) 
            for (int j = 0; j < this.boardWidth; j++) 
            {
                this.cells[i][j] = new Cell();
                this.cells[i][j].addActionListener(this);
                this.cells[i][j].addMouseListener(this);
                this.add(cells[i][j]);
            }
        initBombs();
        initThreat();
    }
    private void initBombs()
    {
        int bombs = 0;
        Random random = new Random();
        do 
        {
            int x = random.nextInt(this.boardHeight);
            int y = random.nextInt(this.boardWidth);
            if(this.cells[x][y].IsBomb() == true)
                continue;
            else
            {
                this.cells[x][y].setBomb();
                bombs++;
            }
        } while (bombs < this.mines);
    }
    private void initThreat()
    {
        for (int i = 0; i < this.boardHeight; i++) 
            for (int j = 0; j < this.boardWidth; j++) 
            {
                if(this.cells[i][j].IsBomb() == true)
                    continue;
                else
                {
                    int danger = this.dangercalculator(i,j);
                    this.cells[i][j].setState(danger);
                }
            }
    }
    private int dangercalculator(int x, int y)
    {
        int danger = 0;
        if(y+1 < this.boardWidth)
            if(cells[x][y+1].IsBomb())
                danger++;
        if(x+1 < this.boardHeight && y+1 < this.boardWidth)
            if(cells[x+1][y+1].IsBomb())
                danger++;
        if(x+1 < this.boardHeight)
            if(cells[x+1][y].IsBomb())
                danger++;
        if(x+1 < this.boardHeight && y-1 > -1)
            if(cells[x+1][y-1].IsBomb())
                danger++;
        if(y-1 > -1)
            if(cells[x][y-1].IsBomb())
                danger++;
        if(x-1 > -1 && y-1 > -1)        
            if(cells[x-1][y-1].IsBomb())
                danger++;
        if(x-1 > -1)
            if(cells[x-1][y].IsBomb())
                danger++;
        if(x-1 > -1 && y+1 < this.boardWidth)
            if(cells[x-1][y+1].IsBomb())
                danger++;
        return danger;
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        Cell button = (Cell) e.getSource();
        button.setClicked();
        if(button.isFree())
        {
            int[] index = findIndex(button);
            int x = index[0];
            int y = index[1];
            if(y+1 < this.boardWidth)
                if(cells[x][y+1].isEnabled())
                    cells[x][y+1].doClick();
            if(x+1 < this.boardHeight && y+1 < this.boardWidth)
                if(cells[x+1][y+1].isEnabled())
                    cells[x+1][y+1].doClick();
            if(x+1 < this.boardHeight)
                if(cells[x+1][y].isEnabled())
                    cells[x+1][y].doClick();
            if(x+1 < this.boardHeight && y-1 > -1)
                if(cells[x+1][y-1].isEnabled())
                    cells[x+1][y-1].doClick();
            if(y-1 > -1)
                if(cells[x][y-1].isEnabled())
                    cells[x][y-1].doClick();
            if(x-1 > -1 && y-1 > -1)        
                if(cells[x-1][y-1].isEnabled())
                    cells[x-1][y-1].doClick();
            if(x-1 > -1)
                if(cells[x-1][y].isEnabled())
                    cells[x-1][y].doClick();
            if(x-1 > -1 && y+1 < this.boardWidth)
                if(cells[x-1][y+1].isEnabled())
                    cells[x-1][y+1].doClick();
        }   
    }

    private int[] findIndex(JButton button)
    {
        int[] index = new int[]{0,0};
        for (int i = 0; i < this.boardHeight; i++) 
            for (int j = 0; j < this.boardWidth; j++) 
                if(cells[i][j].equals(button))
                    index = new int[]{i,j};
        return index;
    }

    @Override
    public void mouseClicked(MouseEvent e) 
    {
        if(e.getButton() == 3)
        {
            Cell button = (Cell) e.getSource();
            if(!(!button.isEnabled() && !button.isFlaged()) && !gameOverFlag)
                button.setFlaged(!button.isFlaged());
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