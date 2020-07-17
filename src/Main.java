
import java.awt.EventQueue;
import java.io.IOException;

public class Main extends Game {

    public static void main(String[] args) throws IOException, ClassNotFoundException  {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                new Game();
            }
        });
    }
}


