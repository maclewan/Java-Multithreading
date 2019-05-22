import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Wilk extends JFrame implements ActionListener {

    private int startingNumberOfRabbits=0;
    private int xSize=0;
    private int ySize=0;
    private double k;
    private Wolf wolf;
    private ArrayList<Rabbit> zajacList;

    private JTextField giveK;
    private JTextField giveX;
    private JTextField giveY;
    private JTextField giveRabbitsNumber;

    private JTextArea podajK;
    private JTextArea podajX;
    private JTextArea podajY;
    private JTextArea podajRabbitsNumber;

    private JButton start;

    public Wilk(){
        super("Symulacja");
        setLayout(null);
        setSize(900,700);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        zajacList = new ArrayList<Rabbit>();
        wolf = new Wolf();

        giveK = new JTextField();
        giveX = new JTextField();
        giveY= new JTextField();
        giveRabbitsNumber= new JTextField();
        podajK = new JTextArea();
        podajX = new JTextArea();
        podajY = new JTextArea();
        podajRabbitsNumber = new JTextArea();
        start = new JButton("Start");

        add(giveK);
        add(giveRabbitsNumber);
        add(giveX);
        add(giveY);
        add(start);
        add(podajK);
        add(podajRabbitsNumber);
        add(podajX);
        add(podajY);

        

        {  //dodanie planszy
            Plansza plansza = new Plansza(30, 30, 5);
            add(plansza);
            plansza.setBounds(0, 0, 600, 600);
        }

    }






    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
