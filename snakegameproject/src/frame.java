import javax.swing.*;
import javax.swing.JFrame;

public class frame extends JFrame {
    frame(){
//        set the title of the frame
        this.setTitle("snake");
//        adding the panel to the frame
        this.add(new panel());
//        this property is false by default
        this.setVisible(true);
        this.setResizable(false);
        this.pack();
    }
}
