import javax.swing.*;
public class App {
    public static void main(String[] args) throws Exception {
    int boardWidth=360;
    int boardHeight=640;
    JFrame frame=new JFrame("Bujji In Space");
   // frame.setVisible(true);
    frame.setSize(boardWidth,boardHeight);
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    BujjiCar bujjicar=new BujjiCar();
    frame.add(bujjicar);
    frame.pack();
    bujjicar.requestFocus();
    frame.setVisible(true);
    
    }
}
