import com.sun.media.jfxmediaimpl.platform.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.*;

import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    @FXML
    Slider sliderBright, sliderCont;
    @FXML
    TextField brightVal, pingIp, contVal, dispName;
    @FXML
    TextArea term;
    @FXML
    Button btnRunPing, btnStopPing;

    @FXML
    TextField textInfo0, textInfo1, textInfo2, textInfo3, textInfo4, textInfo5, textInfo6, textInfo7;

    static Ping ping;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        //Slider brightness inits
        sliderBright.setMin(0);
        sliderBright.setMax(10);
        sliderBright.setValue(6);
        sliderBright.setShowTickLabels(true);
        sliderBright.setShowTickMarks(true);
        sliderBright.setBlockIncrement(0.5);
        brightVal.setText("0.60");

        //slider Contrast inits
        sliderCont.setMin(0);
        sliderCont.setMax(2);
        sliderCont.setValue(1);
        sliderCont.setShowTickLabels(true);
        sliderCont.setShowTickMarks(true);
        sliderCont.setBlockIncrement(0.5);
        contVal.setText("1.00");


        //TextFields
        brightVal.setEditable(false);
        brightVal.setMaxWidth(50);
        contVal.setEditable(false);
        contVal.setMaxWidth(50);
        dispName.setMaxWidth(95);
        dispName.setText("HDMI-A-0");

        btnStopPing.setDisable(true);

        //info fields
        textInfo0.setEditable(false); textInfo1.setEditable(false);
        textInfo3.setEditable(false); textInfo2.setEditable(false);
        textInfo4.setEditable(false); textInfo5.setEditable(false);
        textInfo7.setEditable(false); textInfo6.setEditable(false);



        term.setEditable(false);

        try {
            setInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void setBrightVal(MouseEvent mouseEvent) throws IOException
    {
        brightness();
    }

    public void brightness() throws  IOException
    {
        String args[] = {"xrandr", "--output", dispName.getText() ,
                "--brightness", brightVal.getText()};

        brightVal.setText(String.valueOf(sliderBright
                .getValue()/10).substring(0, 4));


        ProcessBuilder p = new ProcessBuilder(Arrays.asList(args));
        Process start = p.start();
    }

    public void setContVal(MouseEvent mouseEvent) throws IOException
    {
        contVal.setText(String.valueOf(sliderCont.getValue())
                .substring(0, 4));

        String args[] = {"xgamma", "-gamma", contVal.getText()};
        ProcessBuilder p = new ProcessBuilder(Arrays.asList(args));
        Process start = p.start();
        brightness();
    }

    public void execPing(ActionEvent actionEvent)
    {
        term.clear();
        btnRunPing.setDisable(true);
        btnStopPing.setDisable(false);
        String ip = pingIp.getText().toString();
        System.out.println(ip);
        ping = new Ping(ip, term);
        ping.start();
    }


    public void stopPing(ActionEvent actionEvent)
    {
        btnRunPing.setDisable(false);
        btnStopPing.setDisable(true);
        ping.interrupt();
        Ping.start.destroy();

    }

    public void setInfo() throws IOException
    {
        String args[] = {"screenfetch", "-nN"};
        ProcessBuilder p = new ProcessBuilder(Arrays.asList(args));
        Process start = p.start();
        int c = 0;
        Scanner sc = new Scanner(start.getInputStream());
        while(sc.hasNextLine())
        {
            String tmp = sc.nextLine();
            switch (c)
            {
                case 1:
                    textInfo0.setText(tmp.substring(tmp.indexOf(':')+2));
                    break;
                case 2:
                    textInfo1.setText(tmp.substring(tmp.indexOf(':')+2));
                    break;
                case 6:
                    textInfo2.setText(tmp.substring(tmp.indexOf(':')+2));
                    break;
                case 7:
                    textInfo3.setText(tmp.substring(tmp.indexOf(':')+2));
                    break;
                case 13:
                    textInfo4.setText(tmp.substring(tmp.indexOf(':')+2));
                    break;
                case 14:
                    textInfo5.setText(tmp.substring(tmp.indexOf(':')+2));
                    break;
                case 15:
                    textInfo6.setText(tmp.substring(tmp.indexOf(':')+2));
                    break;
                case 3:
                    textInfo7.setText(tmp.substring(tmp.indexOf(':')+2));
                    break;

            }
            c = c+1;
        }
    }

    public void menuClose(ActionEvent actionEvent)
    {
        Controller.ping.interrupt();
        System.exit(0);
    }
}
