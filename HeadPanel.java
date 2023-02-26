import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.Font;

public class HeadPanel extends JPanel
{
    private JButton restartButton;
    private JLabel timerLabel;
    private JLabel livesLabel;
    private JLabel minesLabel;
    private Thread timeThread;
    private int seconds;
    private int minutes;
    private boolean threadFlag;

    private int headPanelWidth;
    private int numberOfBombs;

    public HeadPanel(int headPanelWidth, int numberOfBombs)
    {
        this.headPanelWidth = headPanelWidth;
        this.numberOfBombs = numberOfBombs;
        this.setPreferredSize(new Dimension(this.headPanelWidth*40, 80));
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.setBackground(Color.gray);
        this.initPanel();
        this.threadFlag = true;
        this.timerFix();
    }

    public JButton getRestartJButton()
    {
        return this.restartButton;
    }
    public JLabel getTimerJLabel()
    {
        return this.timerLabel;
    }
    public JLabel getLivesJLabel()
    {
        return livesLabel;
    }
    public JLabel getMinesJLabel()
    {
        return minesLabel;
    }
    
    private void initPanel() 
    {
        this.timerLabel = new JLabel("00:00");
        this.timerLabel.setPreferredSize(new Dimension(80,50));
        this.timerLabel.setBackground(Color.black);
        this.timerLabel.setOpaque(true);
        this.timerLabel.setForeground(Color.red);
        this.timerLabel.setFont(new Font("Serif", Font.BOLD, 33));
        this.timerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        this.minesLabel = new JLabel(String.valueOf(this.numberOfBombs));
        this.minesLabel.setPreferredSize(new Dimension(40,50));
        this.minesLabel.setBackground(Color.black);
        this.minesLabel.setOpaque(true);
        this.minesLabel.setForeground(Color.red);
        this.minesLabel.setFont(new Font("Serif", Font.BOLD, 33));
        this.minesLabel.setHorizontalAlignment(SwingConstants.CENTER);

        this.livesLabel = new JLabel("2");
        this.livesLabel.setPreferredSize(new Dimension(40,50));
        this.livesLabel.setBackground(Color.black);
        this.livesLabel.setOpaque(true);
        this.livesLabel.setForeground(Color.red);
        this.livesLabel.setFont(new Font("Serif", Font.BOLD, 33));
        this.livesLabel.setHorizontalAlignment(SwingConstants.CENTER);

        this.restartButton = new JButton();
        this.restartButton.setPreferredSize(new Dimension(40,40));
        this.restartButton.setMaximumSize(new Dimension(40,40));
        this.restartButton.setIcon(this.iconSizeFix("src/Images/restart.png", 40, 40));

        this.add(Box.createRigidArea(new Dimension(10,0)));
        this.add(this.timerLabel);
        this.add(Box.createHorizontalGlue());
        this.add(this.restartButton);
        this.add(Box.createRigidArea(new Dimension((this.headPanelWidth*40-240)/2,0)));
        this.add(this.minesLabel);
        this.add(Box.createRigidArea(new Dimension(10,0)));
        this.add(this.livesLabel);
        this.add(Box.createRigidArea(new Dimension(10,0)));
    }
    private Icon iconSizeFix(String path, int width, int height)
    {
        ImageIcon firstIcon = new ImageIcon(path); 
        Image firstImage = firstIcon.getImage();
        Image resizedImage = firstImage.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        Icon resizedIcon = new ImageIcon(resizedImage);
        return resizedIcon;
    }
    private void timerFix()
    {
        Runnable runnable = new Runnable() 
            {
                @Override
                public void run() 
                {
                    seconds = 0;
                    minutes = 0;
                    while (threadFlag) 
                    {
                        String time;
                        if(seconds<10)
                            time = String.valueOf(minutes)+":0"+String.valueOf(seconds);
                        else
                            time = String.valueOf(minutes)+":"+String.valueOf(seconds);
                        timerLabel.setText(time);
                        try 
                        {
                            Thread.sleep(1000);
                            seconds++;
                            if(seconds == 60)
                            {
                                seconds = 0;
                                minutes++;
                            }
                        }
                        catch (InterruptedException e) 
                        {
                            e.printStackTrace();
                        }
                    }
                }
            };
        this.timeThread = new Thread(runnable);
        this.timeThread.start();   
    }

    public void restart()
    {
        if(this.threadFlag == false)
        {
            this.threadFlag = true;
            this.timerFix();
        }
        else
        {
            this.seconds = 0;
            this.minutes = 0;
        }
        this.livesLabel.setText("2");
        this.minesLabel.setText(String.valueOf(this.numberOfBombs));
    }

    public void gameOver(boolean win)
    {
        this.threadFlag = false;
        if(win)
            this.minesLabel.setText("0");
    }  
}
