import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Image;
import java.awt.Dimension;

public class Cell extends JButton
{
    private Icon back;
    private Icon flag;

    private Icon mine; 
    private Icon empty;
    private Icon number1;
    private Icon number2;
    private Icon number3;
    private Icon number4;
    private Icon number5;
    private Icon number6;
    private Icon number7;
    private Icon number8;

    private Icon openIcon;

    private boolean bomb;
    private boolean flaged;
    private boolean free;

    public Cell()
    {
        init();
        this.setIcon(this.back);
        this.setPreferredSize(new Dimension(40,40));
        this.openIcon = this.empty;
        this.flaged = false;
        this.bomb = false;
        this.free = false;
        
    }
    public void setBomb()
    {
        this.bomb = true;
        this.openIcon = this.mine;
        this.setDisabledIcon(this.mine);
    }
    public void setState(int state)
    {
        switch(state) 
        {
            case 0:
                this.openIcon = this.empty;
                this.free = true;
                break;
            case 1:
                this.openIcon = this.number1;
                break;
            case 2: 
                this.openIcon = this.number2;              
                break;
            case 3:
                this.openIcon = this.number3;
                break;
            case 4:
                this.openIcon = this.number4;
                break;
            case 5:
                this.openIcon = this.number5;
                break;
            case 6:
                this.openIcon = this.number6;
                break;
            case 7:
                this.openIcon = this.number7;
                break;
            case 8:
                this.openIcon = this.number8;
                break;
        }
    }
    public void setClicked()
    {
        this.setDisabledIcon(this.openIcon);
        this.setEnabled(false);
    }
    public void setFlaged(boolean flaged)
    {
        this.flaged = flaged;
        if(flaged == true)
        {
            this.setDisabledIcon(this.flag);
            this.setEnabled(false);
        }
        else
        {
            this.setDisabledIcon(this.openIcon);
            this.setEnabled(true);
        }
    }

    public boolean IsBomb()
    {
        return this.bomb;
    }
    public boolean isFlaged()
    {
        return this.flaged;
    }
    public boolean isFree()
    {
        return this.free;
    }
    
    public void restart()
    {
        this.setEnabled(true);
        this.setFlaged(false);
    }

    public void gameOver()
    {
        this.setDisabledIcon(this.back);
        this.setEnabled(false);
    }

    private void init()
    {
        this.back = iconSizeFix("src/Images/facingDown.png", 40, 40);
        this.flag = iconSizeFix("src/Images/flagged.png", 40, 40); 
        this.mine = iconSizeFix("src/Images/bomb.png", 40, 40);
        this.empty = iconSizeFix("scr/Images/0.png", 40, 40);
        this.number1 = iconSizeFix("src/Images/1.png", 40, 40);
        this.number2 = iconSizeFix("src/Images/2.png", 40, 40);
        this.number3 = iconSizeFix("src/Images/3.png", 40, 40);
        this.number4 = iconSizeFix("src/Images/4.png", 40, 40);
        this.number5 = iconSizeFix("src/Images/5.png", 40, 40);
        this.number6 = iconSizeFix("src/Images/6.png", 40, 40);
        this.number7 = iconSizeFix("src/Images/7.png", 40, 40);
        this.number8 = iconSizeFix("src/Images/8.png", 40, 40);
    }
    private Icon iconSizeFix(String path, int width, int height)
    {
        ImageIcon firstIcon = new ImageIcon(path); 
        Image firstImage = firstIcon.getImage();
        Image resizedImage = firstImage.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
        Icon resizedIcon = new ImageIcon(resizedImage);
        return resizedIcon;
    }    
}