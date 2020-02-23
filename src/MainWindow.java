import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
public MainWindow(){
setTitle("Snake");
setDefaultCloseOperation(EXIT_ON_CLOSE);
setSize(320,345);
setLocation(400,400);
add(new GameField_Fixed<>());
setVisible(true);

}

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();

    }
    }

