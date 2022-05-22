package pack;
import javax.swing.JFrame;
import java.awt.*;

public class Main {

    public static void main(String[] args){

        /**
         * einfaches Frame auf das zum testen ein Maze gesetzt wird
         */
        Maze m = new Maze();

        JFrame f = new JFrame();
        f.setSize(600,600);
        f.add(m);
        f.setVisible(true);

    }
}
