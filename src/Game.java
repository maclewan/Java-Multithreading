import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class Game extends JFrame implements ActionListener {

    private int startingNumberOfRabbits=0;
    private int xSize=0;
    private int ySize=0;
    private int killedNumberOfRabbits;
    private long startingTime;
    private long endTime;
    private double k;
    private boolean justKilledRabbit=false;

    private Wolf wolf;
    private Board board;
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
    private JTextArea runningTime;

    private JButton start;
    private JButton stop;

    protected Random randomGenerator = new Random();

    public Game(){
        super("Symulacja");
        setLayout(null);
        setSize(1000,900);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        killedNumberOfRabbits=0;

        rabbitsList = new ArrayList<Rabbit>();
        wolf = new Wolf();


        giveK = new JTextField("105");
        giveX = new JTextField("10");
        giveY= new JTextField("8");
        giveRabbitsNumber= new JTextField("6");
        podajK = new JTextArea("Podaj wartość opóźnienia k:");
        podajX = new JTextArea("Podaj X");
        podajY = new JTextArea("Podaj Y");
        podajRabbitsNumber = new JTextArea("Podaj ilość zajęcy");
        remainedRabbits = new JTextArea("Pozostało zajęcy: 0");
        killedRabits = new JTextArea("Zabito zajęcy: 0");
        runningTime = new JTextArea("Czas pracy programu: ");
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
        add(remainedRabbits);
        add(killedRabits);
        add(runningTime);
        add(start);
        add(stop);

        podajX.setBackground(getBackground());
        podajX.setBounds(810,10, 150,19);
        podajX.setEditable(false);

        giveX.setBounds(810,30,100,19);

        podajY.setBounds(810, 50, 150,19);
        podajY.setBackground(getBackground());
        podajY.setEditable(false);

        giveY.setBounds(810,70,100,19);

        podajK.setBackground(getBackground());
        podajK.setEditable(false);
        podajK.setBounds(810, 90, 250, 19);

        giveK.setBounds(810,110,100,19);

        podajRabbitsNumber.setBackground(getBackground());
        podajRabbitsNumber.setEditable(false);
        podajRabbitsNumber.setBounds(810, 130, 150, 19);

        giveRabbitsNumber.setBounds(810,150,100,19);

        remainedRabbits.setBackground(getBackground());
        remainedRabbits.setBounds(10,805,200,19);
        remainedRabbits.setEditable(false);

        killedRabits.setBackground(getBackground());
        killedRabits.setBounds(10,825, 200,19);
        killedRabits.setEditable(false);

        runningTime.setBackground(getBackground());
        runningTime.setBounds(300,805, 400,19);
        runningTime.setEditable(false);
        runningTime.setVisible(false);

        start.setBounds(810,180,100,24);
        start.addActionListener(this);

        stop.setBounds(810,210,100,24);
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
            JOptionPane.showMessageDialog(null, "Podaj wartość zmienneprzecinkową dla k [60,500]", "Błąd", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (k < 59 || k > 501) {
            JOptionPane.showMessageDialog(null, "Podaj wartość zmienneprzecinkową dla k [60,500]", "Błąd", JOptionPane.WARNING_MESSAGE);
            return false;
        }


        {
            try {
                remove(board);
            }
            catch (Exception e){}
            board = null;
            board = new Board(xSize, ySize, startingNumberOfRabbits);
            add(board);
            board.setBounds(0, 0, 800, 800);
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
                    if(inRangeOfPlansza(rabbitX-1,rabbitY-1)&& board.isFieldEmpty(rabbitX-1,rabbitY-1)){
                        arrayOfDistances[i][1]= distanceToWolf(rabbitX-1,rabbitY-1);
                    }
                    break;
                case 2:
                    if(inRangeOfPlansza(rabbitX,rabbitY-1)&& board.isFieldEmpty(rabbitX,rabbitY-1)){
                        arrayOfDistances[i][1]= distanceToWolf(rabbitX,rabbitY-1);
                    }
                    break;
                case 3:
                    if(inRangeOfPlansza(rabbitX+1,rabbitY-1)&& board.isFieldEmpty(rabbitX+1,rabbitY-1)){
                        arrayOfDistances[i][1]= distanceToWolf(rabbitX+1,rabbitY-1);
                    }
                    break;
                case 4:
                    if(inRangeOfPlansza(rabbitX+1,rabbitY)&& board.isFieldEmpty(rabbitX+1,rabbitY)){
                        arrayOfDistances[i][1]= distanceToWolf(rabbitX+1,rabbitY);
                    }
                    break;
                case 5:
                    if(inRangeOfPlansza(rabbitX+1,rabbitY+1)&& board.isFieldEmpty(rabbitX+1,rabbitY+1)){
                        arrayOfDistances[i][1]= distanceToWolf(rabbitX+1,rabbitY+1);
                    }
                    break;
                case 6:
                    if(inRangeOfPlansza(rabbitX,rabbitY+1)&& board.isFieldEmpty(rabbitX,rabbitY+1)){
                        arrayOfDistances[i][1]= distanceToWolf(rabbitX,rabbitY+1);
                    }
                    break;
                case 7:
                    if(inRangeOfPlansza(rabbitX-1,rabbitY+1)&& board.isFieldEmpty(rabbitX-1,rabbitY+1)){
                        arrayOfDistances[i][1]= distanceToWolf(rabbitX-1,rabbitY+1);
                    }
                    break;
                case 8:
                    if(inRangeOfPlansza(rabbitX-1,rabbitY)&& board.isFieldEmpty(rabbitX-1,rabbitY)){
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

        board.buttonsArray[r.getxCoord()][r.getyCoord()].setBackground(board.defaultColor);
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
        board.buttonsArray[r.getxCoord()][r.getyCoord()].setBackground(board.rabbitColor);
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

        for (int i = 0; i < rabbitsList.size(); i++) {
            double temp = distanceToWolf(rabbitsList.get(i).getxCoord(), rabbitsList.get(i).getyCoord());
            distancesToRabbits.add(temp);
            if (temp <= distancesToRabbits.get(closetRabbitIndex)) {
                closetRabbitIndex = i;
            }
        }
        xVector = rabbitsList.get(closetRabbitIndex).getxCoord() - x;
        yVector = rabbitsList.get(closetRabbitIndex).getyCoord() - y;
        board.buttonsArray[x][y].setBackground(board.defaultColor);
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
        if (startingNumberOfRabbits-killedNumberOfRabbits == 0) {
            //endSimulation();
            return;
        }

        board.buttonsArray[wolf.getxCoord()][wolf.getyCoord()].setBackground(board.wolfColor);

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
        endTime=System.currentTimeMillis();
        runningTime.setText("Czas pracy: "+((double)(endTime-startingTime)/1000) +" sekund");
        runningTime.setVisible(true);
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
        threadRabbitList =null;
        threadRabbitList = new ArrayList<>();
        rabbitsList =null;
        rabbitsList = new ArrayList<>();
        for(int i=0; i<startingNumberOfRabbits; i++){
            while(true) {
                newX=randomGenerator.nextInt(xSize);
                newY=randomGenerator.nextInt(ySize);
                if(board.buttonsArray[newX][newY].getBackground()== board.defaultColor) {
                    rabbitsList.add(new Rabbit());
                    rabbitsList.get(rabbitsList.size()-1).setxCoord(newX);
                    rabbitsList.get(rabbitsList.size()-1).setyCoord(newY);
                    board.buttonsArray[newX][newY].setBackground(board.rabbitColor);
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
            if(board.buttonsArray[newX][newY].getBackground()== board.defaultColor) {
                wolf =null;
                wolf = new Wolf();
                wolf.setxCoord(newX);
                wolf.setyCoord(newY);
                board.buttonsArray[newX][newY].setBackground(board.wolfColor);
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
                runningTime.setVisible(false);
                startingTime= System.currentTimeMillis();
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
                moveRabbit(rabbitsList.get(rabbitPointer));
                try {
                    Thread.sleep((long)(k*(randomGenerator.nextDouble()+0.5)));
                }
                catch(InterruptedException interruptedEx){}

            }
        }

        public void setRabbitPointer(int rabbitPointer) {
            this.rabbitPointer = rabbitPointer;
        }

        public void stopThr() {
            inLoop =false;
        }
    }


    class ThreadWolf extends Thread{
        boolean inLoop=true;
        @Override
        public void run(){
            while(inLoop){
                if(justKilledRabbit){
                    try {
                        justKilledRabbit=false;
                        Thread.sleep((long)(5*k*(randomGenerator.nextDouble()+0.5)));
                    }
                    catch(InterruptedException interruptedEx){
                    }
                } //waiting

                if(startingNumberOfRabbits-killedNumberOfRabbits==0){
                    endSimulation();
                }  //end of simulation

                moveWolf();
                try {
                    Thread.sleep((long)(k*(randomGenerator.nextDouble()+0.5)));
                }
                catch(InterruptedException interruptedEx){}

            }
        }
        public void stopThr() {
            inLoop =false;
        }
    }

}



