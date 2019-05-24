import javax.swing.*;
import java.awt.*;

public class Plansza extends JPanel{
    JButton[][] buttonsArray;
    Color defaultColor = new Color(100,100,100);
    Color rabbitColor = new Color(1, 250,1);
    Color wolfColor = new Color(250, 1, 1);
    Color backgroundColor = new Color( 1);
    public Plansza(){
        setPreferredSize(new Dimension(600, 600));
        setLayout(null);

    }
    public Plansza(int xSize, int ySize, int startingNumberOfRabbits){
        setPreferredSize(new Dimension(600, 600));
        setLayout(null);
        setBackground(backgroundColor);

        int buttonWidth=600/xSize;
        int buttonHeiht=600/ySize;


        buttonsArray= new JButton[xSize][ySize];
        for(int i=0;i<xSize;i++){
            for(int j=0;j<ySize;j++){

                buttonsArray[i][j]=new JButton();
                add(buttonsArray[i][j]);
                buttonsArray[i][j].setBounds(1+i*buttonWidth,1+j*buttonHeiht,buttonWidth-2, buttonHeiht-2);
                //buttonsArray[i][j].setEnabled(false);
                buttonsArray[i][j].setBackground(defaultColor);
            }
        } //nowe przyciski w tablicy przycisków i rozmieszcza je

    }

}
