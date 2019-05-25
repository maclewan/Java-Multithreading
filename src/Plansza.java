import javax.swing.*;
import java.awt.*;

public class Plansza extends JPanel {
    JButton[][] buttonsArray;
    Color defaultColor = new Color(15, 150, 29);
    Color rabbitColor = new Color(88, 93, 73);
    Color wolfColor = new Color(179, 179, 179);

    public Plansza(int xSize, int ySize, int startingNumberOfRabbits) {
        setPreferredSize(new Dimension(600, 600));
        setLayout(null);

        int buttonWidth = 600 / xSize;
        int buttonHeiht = 600 / ySize;


        buttonsArray = new JButton[xSize][ySize];
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {

                buttonsArray[i][j] = new JButton();
                add(buttonsArray[i][j]);
                buttonsArray[i][j].setBounds(1 + i * buttonWidth, 1 + j * buttonHeiht, buttonWidth - 2, buttonHeiht - 2);
                buttonsArray[i][j].setEnabled(false);
                buttonsArray[i][j].setBackground(defaultColor);
            }
        } //nowe przyciski w tablicy przycisków i rozmieszcza je

    }

    public boolean isFieldEmpty(int x, int y) {
        if (buttonsArray[x][y].getBackground() == defaultColor) {
            return true;
        }
        return false;
    }

}