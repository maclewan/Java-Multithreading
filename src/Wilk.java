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
        add(start);

        podajX.setText("Podaj X");
        podajX.setBackground(getBackground());
        podajX.setBounds(610,10, 150,19);
        podajX.setEditable(false);

        giveX.setBounds(610,30,100,19);

        podajY.setText("Podaj Y");
        podajY.setBounds(610, 50, 150,19);
        podajY.setBackground(getBackground());
        podajY.setEditable(false);

        giveY.setBounds(610,70,100,19);

        podajK.setText("Podaj K");
        podajK.setBackground(getBackground());
        podajK.setEditable(false);
        podajK.setBounds(610, 90, 150, 19);

        giveK.setBounds(610,110,100,19);

        podajRabbitsNumber.setText("Podaj ilość zajęcy");
        podajRabbitsNumber.setBackground(getBackground());
        podajRabbitsNumber.setEditable(false);
        podajRabbitsNumber.setBounds(610, 130, 150, 19);

        giveRabbitsNumber.setBounds(610,150,100,19);

        start.setBounds(610,180,100,24);

        {  //dodanie planszy
            Plansza plansza = new Plansza(30, 30, 5);
            add(plansza);
            plansza.setBounds(0, 0, 600, 600);
        }

    }

    public boolean validateData(String getx,String gety,String getRabbitsNumber, String getk){

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

        try{
            k=Double.parseDouble(getk);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog( null,"Podaj wartość zmienneprzecinkową dla k","Błąd",JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }






    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
