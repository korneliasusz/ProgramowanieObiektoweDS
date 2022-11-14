package agh.ics.oop;
import javax.swing.*;

// niedokonczone - nie dziala poprawnie
public class SwingAnimation {

    JFrame f = new JFrame();
    JTextArea ta;

    SwingAnimation(IWorldMap map) {
        ta = new JTextArea(map.toString());
        ta.setBounds(25,25,300,300);
        ta.setEditable(false);
        f.add(ta);

        f.setSize(400, 400);
        f.setLayout(null);
        f.setVisible(true);


        new Timer(3000, (e) -> { f.setVisible(true); }).start();
        new Timer(3000, (e) -> { f.dispose(); }).start();

    }


}
