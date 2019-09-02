import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.util.*;
public class Ping extends Thread
{
    String ip;

    static Process start;
    TextArea term;
    public Ping(String ip, TextArea term)
    {
        this.ip = ip;
        this.term = term;
        start = null;
    }

    public void run()
    {
        String args[] = {"ping", ip, "-O"};

        try
        {
            ProcessBuilder p =new ProcessBuilder(Arrays.asList(args));
            start = p.start();

            Scanner sc = new Scanner(start.getInputStream());
            while(sc.hasNextLine())
            {
                String op = sc.nextLine();
                //System.out.println(op);
                term.appendText(op + "\n");
            }
        } catch (Exception e)
        {
            System.out.println("Exception");

            Ping.start.destroy();
        }

    }
}
