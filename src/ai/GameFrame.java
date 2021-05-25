package ai;
import javax.swing.*;

public class GameFrame extends JFrame {
    private GamePanel gamepanel;
    public GameFrame(){
        gamepanel = new GamePanel(720,720,this);
        add(gamepanel);
        setTitle("Co ganh");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(720, 720);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void refresh(JPanel view){
        this.getContentPane().removeAll();
        this.getContentPane().add(view);
        this.repaint();
        this.revalidate();
        this.setVisible(true);
    }


    public static void main(String args[]) {
        new GameFrame();
    }
}
