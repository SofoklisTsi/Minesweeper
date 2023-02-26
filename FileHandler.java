import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileHandler 
{
    private File f1;
    private String[] scores;
    private Scanner in;

    public FileHandler(File f1)
    {
        this.f1 = f1;
        initFile();
    }

    public String[] getScores()
    {
        return scores;
    }
    public void setScores(String[] scores)
    {
        this.scores = scores;
    }

    public void saveScores()
    {
        try 
        {
            FileWriter out = new FileWriter(f1);
            for (String scoreString : scores) 
            {
                out.write(scoreString+"\n");
            }
            out.close();
        } 
        catch (IOException e) 
        {
            System.out.println("Writer Couldn't Find File");
        }    
    }
    
    private void initFile()
    {
        this.scores = new String[3];
        try 
        {
            if(this.f1.createNewFile())
            {
                this.scores[0] = "Null";
                this.scores[1] = "Null";
                this.scores[2] = "Null";
            }
            else 
            {
                this.in = new Scanner(f1);
                for (int i = 0; i < 3; i++) 
                    this.scores[i] = in.nextLine();
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}
