import javax.swing.*;
import java.awt.*;

public class Plansza extends JPanel{
    JToggleButton[][] buttonsArray;

    public Plansza(int xSize, int ySize, int startingNumberOfRabbits){
        setPreferredSize(new Dimension(600, 600));
        setLayout(null);
        int buttonWidth=600/xSize;
        int buttonHeiht=600/ySize;

        buttonsArray= new JToggleButton[xSize][ySize];
        for(int i=0;i<xSize;i++){
            for(int j=0;j<ySize;j++){
                buttonsArray[i][j]=new JToggleButton();
                
            }
        } //nowe przyciski w tablicy przycisków i rozmieszcza je




    }

}
