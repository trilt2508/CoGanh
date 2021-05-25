package ai;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;


public class GamePanel extends JPanel implements MouseListener ,Runnable {
    public static int width;
    public static int height;
    private boolean running = false;
    private Thread thread;
    private BufferedImage bufferedImage;
    private Graphics2D bufG2D;
    private Board board;
    private mouseInput MouseClick;
    private GameFrame gameFrame;




    public GamePanel(int width, int height,GameFrame gameFrame) {
        MouseClick = new mouseInput(board,this);
        this.gameFrame = gameFrame;
        this.width = width;
        this.height = height;
        board = new Board();
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        requestFocus();
        running = true;
        bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        addMouseListener(this);
        newThread();
    }
    public void newThread() {
        if (thread == null) {
            thread = new Thread(this, "Game thread");
            thread.start();
        }
    }

    public void run() {

        final double GAME_HERZT = 60.0;
        final double TBU = 1000000000/GAME_HERZT; // time before update
        final int MUBR =5; // must update before render

        double lastUpdateTime = System.nanoTime();
        double lastRenderTime;

        final double TARGET_FPS = 60;
        final double TTBR = 1000000000/TARGET_FPS; // total time before render;

        int frameCount=0;
        int lastSecondTime = (int) (lastUpdateTime/1000000000);
        int oldFrameCount=0;


        while (running) {
            double now = System.nanoTime();
            int updateCount=0;
            while((now -lastUpdateTime>TBU && updateCount<MUBR)) {
                update();
                lastUpdateTime += TBU;
                updateCount++;
            }
            if (now - lastUpdateTime > TBU){
                lastUpdateTime = now - TBU;
            }
            render();
            repaint();
            lastRenderTime = now;
            frameCount++;

            int thisSecond= (int) (lastUpdateTime/1000000000);
            if (thisSecond > lastSecondTime){
                if (frameCount != oldFrameCount){
                    System.out.println("NEW SECOND "+ thisSecond+" "+ frameCount);
                    oldFrameCount = frameCount;
                }
                frameCount=0;
                lastSecondTime = thisSecond;
            }

            while (now-lastRenderTime<TTBR && now-lastUpdateTime<TBU){
                Thread.yield();

                try{
                    Thread.sleep(1);
                } catch (Exception e){
                    System.out.println("EROR : yielding thread");
                }
                now = System.nanoTime();
            }
        }
    }

    public void update() {
        board.update();
        if (board.gameOver()) {
            running = false;
            render();
            Button b = new Button("New Game");
            b.setFont(new Font("Tahoma",Font.CENTER_BASELINE,30));
            b.setBounds(260, 335, 200, 100);
            add(b);
            b.addActionListener(e -> {
               gameFrame.refresh(new GamePanel(width,height,gameFrame));
            });
        }
    }

    public void render() {
        if (bufferedImage == null) {
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        } else {
            bufG2D = (Graphics2D) bufferedImage.getGraphics();
        }

        if (bufG2D != null) {
            bufG2D.fillRect(0,0,720,720);
            board.draw(bufG2D);
            if (running==false) {
                bufG2D.setFont(new Font("Tahoma",Font.BOLD,50));
                bufG2D.setColor(Color.RED);
                bufG2D.drawString("GAME OVER",200,200);
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (board.turn == -1) {
            MouseClick.processMouseInput(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void paint(Graphics g) {
        g.drawImage(bufferedImage, 0, 0, this);
    }


}
