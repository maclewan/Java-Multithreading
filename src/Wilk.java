import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static java.lang.Integer.max;
import static java.lang.Integer.parseInt;

public class Wilk extends JFrame implements ActionListener {

    private int startingNumberOfRabbits=0;
    private int xSize=0;
    private int ySize=0;
    private int killedNumberOfRabbits;
    private double k;
    private boolean justKilledRabbit=false;

    private Wolf wolf;
    private Plansza plansza;
    private ArrayList<Rabbit> rabbitsList;
    private ArrayList<ThreadRabbit> threadRabbitList;
    private ThreadWolf threadWolf;


    private JTextField giveK;
    private JTextField giveX;
    private JTextField giveY;
    private JTextField giveRabbitsNumber;

    private JTextArea podajK;
    private JTextArea podajX;
    private JTextArea podajY;
    private JTextArea podajRabbitsNumber;
    private JTextArea remainedRabbits;
    private JTextArea killedRabits;

    private JButton start;
    private JButton stop;
    //temp
    private JButton wolfTest;
    private JButton rabbitTest;
    //temp
    protected Random randomGenerator = new Random();

    public Wilk(){
        super("Symulacja");
        setLayout(null);
        setSize(900,700);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        killedNumberOfRabbits=0;

        rabbitsList = new ArrayList<Rabbit>();
        wolf = new Wolf();


        giveK = new JTextField("105");
        giveX = new JTextField("10");
        giveY= new JTextField("8");
        giveRabbitsNumber= new JTextField("6");
        podajK = new JTextArea("Podaj K");
        podajX = new JTextArea("Podaj X");
        podajY = new JTextArea("Podaj Y");
        podajRabbitsNumber = new JTextArea("Podaj ilość zajęcy");
        remainedRabbits = new JTextArea("Pozostało zajęcy: 0");
        killedRabits = new JTextArea("Zabito zajęcy: 0");
        start = new JButton("Start");
        stop = new JButton("Stop");

        //test
        wolfTest=new JButton("Wolf");
        rabbitTest = new JButton("Rabbits");
        add(wolfTest);
        add(rabbitTest);
        wolfTest.addActionListener(this);
        rabbitTest.addActionListener(this);
        wolfTest.setBounds(640,300,100,24);
        rabbitTest.setBounds(640,330,100,24);
        //test

        add(giveK);
        add(giveRabbitsNumber);
        add(giveX);
        add(giveY);
        add(start);
        add(podajK);
        add(podajRabbitsNumber);
        add(podajX);
        add(podajY);
        add(remainedRabbits);
        add(killedRabits);
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

        remainedRabbits.setBackground(getBackground());
        remainedRabbits.setBounds(10,605,200,19);
        remainedRabbits.setEditable(false);

        killedRabits.setBackground(getBackground());
        killedRabits.setBounds(10,625, 200,19);
        killedRabits.setEditable(false);

        start.setBounds(610,180,100,24);
        start.addActionListener(this);

        stop.setBounds(610,210,100,24);
        stop.addActionListener(this);

    }

    public boolean validateData(String getx, String gety, String getRabbitsNumber, String getk) {

        try {
            xSize = Integer.parseInt(getx);
            ySize = Integer.parseInt(gety);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Podaj wartości całkowite dla x i y", "Błąd", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            startingNumberOfRabbits = Integer.parseInt(getRabbitsNumber);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Podaj całkowitą liczbę zajęcy", "Błąd", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (startingNumberOfRabbits >= xSize * ySize) {
            JOptionPane.showMessageDialog(null, "Za dużo zajęcy!", "Błąd", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (xSize > 31 || ySize > 31) {
            JOptionPane.showMessageDialog(null, "Podaj x,y <31", "Błąd", JOptionPane.WARNING_MESSAGE);
            return false;
        }


        try {
            k = Double.parseDouble(getk);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Podaj wartość zmienneprzecinkową dla k [50,500]", "Błąd", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (k < 50 || k > 250) {
            JOptionPane.showMessageDialog(null, "Podaj wartość zmienneprzecinkową dla k [50,250]", "Błąd", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        k=10*k;

        {
            try {
                remove(plansza);
            }
            catch (Exception e){}
            plansza = null;
            plansza = new Plansza(xSize, ySize, startingNumberOfRabbits);
            add(plansza);
            plansza.setBounds(0, 0, 600, 600);
            remainedRabbits.setText("Pozostało zajęcy: "+startingNumberOfRabbits);
            repaint();
            return true;
        }  //tworzenie planszy
    }

    public double distanceToWolf(int rabbitX, int rabbitY){
        return Math.sqrt(Math.pow(wolf.getxCoord()-rabbitX,2)+Math.pow(wolf.getyCoord()-rabbitY,2));
    }

    public boolean inRangeOfPlansza(int xCoord, int yCoord){

        if(xCoord>=0&&xCoord<xSize&&yCoord>=0&&yCoord<ySize){
            return true;
        }
        return false;
    }

    public void sortArray(double[][] arr){
        int n =8;
        double temp = 0;
        double tempIndex=0;

        for(int i = 0; i < n; i++) {
            for(int j=1; j < (n-i); j++) {
                if(arr[j-1][1] > arr[j][1]) {

                    temp = arr[j-1][1];
                    tempIndex = arr[j-1][0];

                    arr[j-1][1] = arr[j][1];
                    arr[j-1][0] = arr[j][0];

                    arr[j][1] = temp;
                    arr[j][0] = tempIndex;
                }
            }
        }
    }

    public synchronized int fieldToJump(int rabbitX, int rabbitY){       //returns on witch (furthest) field should jump rabbit
        double maxValue=0;
        int counter=1;
        double[][] arrayOfDistances=new double[8][2];
        for(int i=0; i<8;i++){
            arrayOfDistances[i][1]=0;
            arrayOfDistances[i][0]=i+1;
            switch(i+1){
                case 1:
                    if(inRangeOfPlansza(rabbitX-1,rabbitY-1)&&plansza.isFieldEmpty(rabbitX-1,rabbitY-1)){
                        arrayOfDistances[i][1]= distanceToWolf(rabbitX-1,rabbitY-1);
                    }
                    break;
                case 2:
                    if(inRangeOfPlansza(rabbitX,rabbitY-1)&&plansza.isFieldEmpty(rabbitX,rabbitY-1)){
                        arrayOfDistances[i][1]= distanceToWolf(rabbitX,rabbitY-1);
                    }
                    break;
                case 3:
                    if(inRangeOfPlansza(rabbitX+1,rabbitY-1)&&plansza.isFieldEmpty(rabbitX+1,rabbitY-1)){
                        arrayOfDistances[i][1]= distanceToWolf(rabbitX+1,rabbitY-1);
                    }
                    break;
                case 4:
                    if(inRangeOfPlansza(rabbitX+1,rabbitY)&&plansza.isFieldEmpty(rabbitX+1,rabbitY)){
                        arrayOfDistances[i][1]= distanceToWolf(rabbitX+1,rabbitY);
                    }
                    break;
                case 5:
                    if(inRangeOfPlansza(rabbitX+1,rabbitY+1)&&plansza.isFieldEmpty(rabbitX+1,rabbitY+1)){
                        arrayOfDistances[i][1]= distanceToWolf(rabbitX+1,rabbitY+1);
                    }
                    break;
                case 6:
                    if(inRangeOfPlansza(rabbitX,rabbitY+1)&&plansza.isFieldEmpty(rabbitX,rabbitY+1)){
                        arrayOfDistances[i][1]= distanceToWolf(rabbitX,rabbitY+1);
                    }
                    break;
                case 7:
                    if(inRangeOfPlansza(rabbitX-1,rabbitY+1)&&plansza.isFieldEmpty(rabbitX-1,rabbitY+1)){
                        arrayOfDistances[i][1]= distanceToWolf(rabbitX-1,rabbitY+1);
                    }
                    break;
                case 8:
                    if(inRangeOfPlansza(rabbitX-1,rabbitY)&&plansza.isFieldEmpty(rabbitX-1,rabbitY)){
                        arrayOfDistances[i][1]= distanceToWolf(rabbitX-1,rabbitY);
                    }
                    break;
                default:

            }
        }
        sortArray(arrayOfDistances);
        if(arrayOfDistances[7][1]==0){
            return 0;
        } //dont move, if there is no possible field to jump on

        //sprawdzenie ile jest takich samych maxów
        maxValue=arrayOfDistances[7][1];
        for(int i=6; i>=0; i--){
            if(arrayOfDistances[i][1]==maxValue){
                counter++;
            }
        }
        //podział zgodnie z rozkładem losowym
        if(counter==1){
            return (int)arrayOfDistances[7][0];
        }
        else{
            return (int)arrayOfDistances[7-(randomGenerator.nextInt(counter))][0];
        }
    }

    private synchronized void moveRabbit(Rabbit r){
        int x=r.getxCoord();
        int y=r.getyCoord();
        int direciton = fieldToJump(r.getxCoord(),r.getyCoord());

        plansza.buttonsArray[r.getxCoord()][r.getyCoord()].setBackground(plansza.defaultColor);
        switch(direciton){
            case 0:
                break;
            case 1:
                r.setxCoord(x-1);
                r.setyCoord(y-1);
                break;
            case 2:
                r.setxCoord(x);
                r.setyCoord(y-1);
                break;
            case 3:
                r.setxCoord(x+1);
                r.setyCoord(y-1);
                break;
            case 4:
                r.setxCoord(x+1);
                r.setyCoord(y);
                break;
            case 5:
                r.setxCoord(x+1);
                r.setyCoord(y+1);
                break;
            case 6:
                r.setxCoord(x);
                r.setyCoord(y+1);
                break;
            case 7:
                r.setxCoord(x-1);
                r.setyCoord(y+1);
                break;
            case 8:
                r.setxCoord(x-1);
                r.setyCoord(y);
                break;
            default:
                break;
        }
        plansza.buttonsArray[r.getxCoord()][r.getyCoord()].setBackground(plansza.rabbitColor);
    }

    private synchronized void moveWolf() {
        if (rabbitsList.size() == 0) {
            endSimulation();
            return;
        }
        int x = wolf.getxCoord();
        int y = wolf.getyCoord();
        int xVector = 0;
        int yVector = 0;
        int closetRabbitIndex = 0;

        ArrayList<Double> distancesToRabbits = new ArrayList<Double>();

        //double hardTemp=distanceToWolf(rabbitsList.get(0).getxCoord(),rabbitsList.get(0).getyCoord());
        for (int i = 0; i < rabbitsList.size(); i++) {
            double temp = distanceToWolf(rabbitsList.get(i).getxCoord(), rabbitsList.get(i).getyCoord());
            distancesToRabbits.add(temp);
            if (temp <= distancesToRabbits.get(closetRabbitIndex)) {
                closetRabbitIndex = i;
            }
        }
        //oblicz wektor, probuj zmniejszyc albo nie powiekszyc, albo stoj
        xVector = rabbitsList.get(closetRabbitIndex).getxCoord() - x;
        yVector = rabbitsList.get(closetRabbitIndex).getyCoord() - y;
        plansza.buttonsArray[x][y].setBackground(plansza.defaultColor);
        {
            if (xVector != 0 && yVector != 0) {
                switch (quaterOfCoord(xVector, yVector)) {
                    case 1:
                        x = x + 1;
                        y = y + 1;
                        break;
                    case 2:
                        x = x + 1;
                        y = y - 1;
                        break;
                    case 3:
                        x = x - 1;
                        y = y - 1;
                        break;
                    case 4:
                        x = x - 1;
                        y = y + 1;
                        break;
                    default:
                        break;
                }
            } else if (xVector == 0) {
                if (yVector < 0) {
                    y = y - 1;
                } else {
                    y = y + 1;
                }
            } else if (yVector == 0) {
                if (xVector < 0) {
                    x = x - 1;
                } else {
                    x = x + 1;
                }
            }
        }  //ruch wilka

        wolf.setxCoord(x);
        wolf.setyCoord(y);
        plansza.buttonsArray[wolf.getxCoord()][wolf.getyCoord()].setBackground(plansza.wolfColor);

        if(ifThereWasRabbit(x,y)>=0){
            defeatZając(ifThereWasRabbit(x,y));
            justKilledRabbit=true;
        }
    }

    private int quaterOfCoord(int x, int y){
        if(x*y>0&&x+y>0){
            return 1;
        }
        else if(x*y>0&&x+y<0){
            return 3;
        }
        else if(x<0){
            return 4;
        }
        else if(y<0){
            return 2;
        }
        else{
            throw new RuntimeException("Coś poszło nie tak!");
        }
    }  //returns quater od coord system where this point was

    private int ifThereWasRabbit(int x, int y){
        for(int i=0;i<rabbitsList.size();i++){
            if(rabbitsList.get(i).getxCoord()==x&&rabbitsList.get(i).getyCoord()==y){
                return i;
            }
        }
        return -1;
    } //returns index of Rabbit if it was there

    private void defeatZając(int i){
        killedNumberOfRabbits++;
        threadRabbitList.get(i).interrupt();
        threadRabbitList.get(i).stopThr();
        rabbitsList.get(i).setyCoord(-100-i);
        rabbitsList.get(i).setxCoord(-100-i);
        remainedRabbits.setText("Pozostało zajęcy: "+(startingNumberOfRabbits-killedNumberOfRabbits));
        killedRabits.setText("Zabito zajęcy: "+(killedNumberOfRabbits));
    }

    private void endSimulation(){

        stop.setVisible(false);
        start.setEnabled(true);
        threadWolf.interrupt();
        threadWolf.stopThr();
        threadWolf =null;
        for(int i=0;i<threadRabbitList.size();i++){
            threadRabbitList.get(i).interrupt();
            threadRabbitList.get(i).stopThr();
        }
        threadRabbitList.clear();
        threadRabbitList=null;
    }

    private void randomSpacingOfAnimals(){
        int newX;
        int newY;
        threadRabbitList = new ArrayList<>();
        for(int i=0; i<startingNumberOfRabbits; i++){
            while(true) {
                newX=randomGenerator.nextInt(xSize);
                newY=randomGenerator.nextInt(ySize);
                if(plansza.buttonsArray[newX][newY].getBackground()==plansza.defaultColor) {
                    rabbitsList.add(new Rabbit());
                    rabbitsList.get(rabbitsList.size()-1).setxCoord(newX);
                    rabbitsList.get(rabbitsList.size()-1).setyCoord(newY);
                    plansza.buttonsArray[newX][newY].setBackground(plansza.rabbitColor);
                    //tworzenie wątków:

                    threadRabbitList.add(new ThreadRabbit());
                    threadRabbitList.get(threadRabbitList.size()-1).setRabbitPointer(i);
                    break;
                }
            }
        }
        while(true) {
            newX=randomGenerator.nextInt(xSize);
            newY=randomGenerator.nextInt(ySize);
            if(plansza.buttonsArray[newX][newY].getBackground()==plansza.defaultColor) {
                wolf =null;
                wolf = new Wolf();
                wolf.setxCoord(newX);
                wolf.setyCoord(newY);
                plansza.buttonsArray[newX][newY].setBackground(plansza.wolfColor);
                threadWolf = new ThreadWolf();
                break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==start) {
            if(validateData(giveX.getText(), giveY.getText(), giveRabbitsNumber.getText(), giveK.getText()))
            {
                stop.setVisible(true);
                start.setEnabled(false);
                justKilledRabbit=true;
                randomSpacingOfAnimals();
                killedNumberOfRabbits=0;
                killedRabits.setText("Zabito zajęcy: "+(killedNumberOfRabbits));
                threadWolf.start();
                for(int i=0;i<threadRabbitList.size();i++){
                    threadRabbitList.get(i).start();
                }

            }

        }
        else if(e.getSource()==stop){
            stop.setVisible(false);
            start.setEnabled(true);
            endSimulation();
        }

    }

    class ThreadRabbit extends Thread {
        int rabbitPointer;
        boolean inLoop=true;
        @Override
        public void run(){
            while(inLoop){
                System.out.println("Lece: "+rabbitPointer);
                moveRabbit(rabbitsList.get(rabbitPointer));
                try {
                    Thread.sleep((long)(k*(randomGenerator.nextDouble()+0.5)));
                }
                catch(InterruptedException interruptedEx){
                    System.out.println("Interrupted Exception! rabbit: "+rabbitPointer);

                }

            }
        }

        public void setRabbitPointer(int rabbitPointer) {
            this.rabbitPointer = rabbitPointer;
        }

        public void stopThr() {
            inLoop =false;
            System.out.println("Stopped: "+ rabbitPointer);
        }
    }

    class ThreadWolf extends Thread{
        boolean inLoop=true;
        @Override
        public void run(){
            while(inLoop){
                if(justKilledRabbit){
                    System.out.println("lece: wilk");
                    try {
                        justKilledRabbit=false;
                        Thread.sleep((long)(5*k*(randomGenerator.nextDouble()+0.5)));
                    }
                    catch(InterruptedException interruptedEx){
                        System.out.println("Interrupted Exception! wolf");
                    }
                }

                if(startingNumberOfRabbits-killedNumberOfRabbits==0){
                    endSimulation();
                }
                moveWolf();
                try {
                    Thread.sleep((long)(k*(randomGenerator.nextDouble()+0.5)));
                }
                catch(InterruptedException interruptedEx){
                }

            }
        }
        public void stopThr() {
            inLoop =false;
            System.out.println("Stopped: wilk");
        }
    }

}



