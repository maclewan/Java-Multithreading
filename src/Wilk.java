import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Wilk extends JFrame implements ActionListener {

    private int startingNumberOfRabbits=0;
    private Wolf wolf;
    private ArrayList<Rabbit> zajacList;

    public Wilk(){
        super("Symulacja");
        setLayout(null);
        zajacList = new ArrayList<Rabbit>();
        wolf = new Wolf();


    }






    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
