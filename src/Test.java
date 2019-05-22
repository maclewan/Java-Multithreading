
import java.awt.EventQueue;
import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException, ClassNotFoundException  {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new Wilk();
            }
        });
    }
}


