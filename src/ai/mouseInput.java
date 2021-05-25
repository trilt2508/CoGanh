package ai;
import java.awt.*;
import java.awt.event.MouseEvent;

public class mouseInput {
    private Board board;
    boolean selectNextMove = false;
    private static Chess selectedChess = new Chess();
    private GamePanel gamePanel;
    public mouseInput(Board board,GamePanel gamePanel) {
        this.board = board;
        this.gamePanel = gamePanel;
    }

    public void processMouseInput (MouseEvent e) {

        if (selectNextMove) {
            if (board.listNextMove.size() > 0) {
                for (nextMove m : board.listNextMove) {
                    int deltaX = 60 + (m.getY() - 1) * 150 - 15 - e.getX();
                    int deltaY = 60 + (m.getX() - 1) * 150 - 15 - e.getY();
                    if (deltaX * deltaX + deltaY * deltaY <= 30 * 30) {
                        board.Matrix[selectedChess.getX()][selectedChess.getY()] = 0;
                        board.Matrix[m.getX()][m.getY()] = board.turn;
                        board.attack(m);
                        //board.attackVay();
                        selectedChess.setX(m.getX());
                        selectedChess.setY(m.getY());
                        for (int i = 1; i <= 5; i++) {
                            for (int j = 1; j <= 5; j++) {
                            	System.out.format("%3d", board.Matrix[i][j]);
                            }
                            System.out.println();
                        }
                        System.out.println();
                        board.listNextMove.clear();
                        selectedChess = null;
                        selectNextMove = false;

                        gamePanel.update();
                        gamePanel.render();
                        gamePanel.repaint();
                        board.turn = 1;
                        return;
                    }
                }
            }
        }

        board.listNextMove.clear();

        for (Chess c : board.listChess) {
            if (c.inRange(e.getX(), e.getY()) && board.Matrix[c.getX()][c.getY()] == board.turn) {
                board.getNextMoveFrom(c);
                selectNextMove = true;
                selectedChess = c;
                return;
            }
        }
    }
}
