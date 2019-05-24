import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

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
    private JButton stop;

    public Wilk(){
        super("Symulacja");
        setLayout(null);
        setSize(900,700);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        zajacList = new ArrayList<Rabbit>();
        wolf = new Wolf();

        giveK = new JTextField("105");
        giveX = new JTextField("10");
        giveY= new JTextField("8");
        giveRabbitsNumber= new JTextField("6");
        podajK = new JTextArea("Podaj K");
        podajX = new JTextArea("Podaj X");
        podajY = new JTextArea("Podaj Y");
        podajRabbitsNumber = new JTextArea("Podaj ilość zajęcy");
        start = new JButton("Start");
        stop = new JButton("Stop");

        add(giveK);
        add(giveRabbitsNumber);
        add(giveX);
        add(giveY);
        add(start);
        add(podajK);
        add(podajRabbitsNumber);
        add(podajX);
        add(podajY);
        add(start);
        add(stop);

        podajX.setBackground(getBackground());
        podajX.setBounds(610,10, 150,19);
        podajX.setEditable(false);

        giveX.setBounds(610,30,100,19);

        podajY.setBounds(610, 50, 150,19);
        podajY.setBackground(getBackground());
        podajY.setEditable(false);

        giveY.setBounds(610,70,100,19);

        podajK.setBackground(getBackground());
        podajK.setEditable(false);
        podajK.setBounds(610, 90, 150, 19);

        giveK.setBounds(610,110,100,19);

        podajRabbitsNumber.setBackground(getBackground());
        podajRabbitsNumber.setEditable(false);
        podajRabbitsNumber.setBounds(610, 130, 150, 19);

        giveRabbitsNumber.setBounds(610,150,100,19);

        start.setBounds(610,180,100,24);
        start.addActionListener(this);

        stop.setBounds(610,210,100,24);
        stop.addActionListener(this);


       // Plansza plansza = new Plansza();
    }

    public boolean validateData(String getx, String gety, String getRabbitsNumber, String getk){

        try{
            xSize=Integer.parseInt(getx);
            ySize=Integer.parseInt(gety);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog( null,"Podaj wartości całkowite dla x i y","Błąd",JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try{
            startingNumberOfRabbits=Integer.parseInt(getRabbitsNumber);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog( null,"Podaj całkowitą liczbę zajęcy","Błąd",JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if(startingNumberOfRabbits>=xSize*ySize){
            JOptionPane.showMessageDialog( null,"Za dużo zajęcy!","Błąd",JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if(xSize>25||ySize>25){
            JOptionPane.showMessageDialog( null,"Podaj x,y <25","Błąd",JOptionPane.WARNING_MESSAGE);
            return false;
        }


        try{
            k=Double.parseDouble(getk);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog( null,"Podaj wartość zmienneprzecinkową dla k [50,500]","Błąd",JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if(k<50||k>500){
            JOptionPane.showMessageDialog( null,"Podaj wartość zmienneprzecinkową dla k [50,500]","Błąd",JOptionPane.WARNING_MESSAGE);
            return false;
        }

        Plansza plansza = new Plansza(xSize, ySize, startingNumberOfRabbits);
        add(plansza);
        plansza.setBounds(0, 0, 600, 600);
        return true;


    }
    void newPlansza(){

    }
    void removeObject(Object object){
        object = null;
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==start) {
            validateData(giveX.getText(), giveY.getText(), giveRabbitsNumber.getText(), giveK.getText());
            stop.setVisible(true);
            start.setEnabled(false);

        }
        else if(e.getSource()==stop){
            stop.setVisible(false);
            start.setEnabled(true);
            removeObject();
        }
    }
}
