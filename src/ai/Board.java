package ai;
import javax.imageio.ImageIO;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class Board {

    public static int Matrix[][] = new int[25][25];
    public static int[][] mark = new int[25][25];
    BufferedImage image, imageRed, imageBlue;
    public static ArrayList<Chess> listChess = new ArrayList<>();
    public static ArrayList<nextMove> listNextMove = new ArrayList<>();
    public static int turn;

    public Board() {
    	
    	for(int i = 1; i <= 5; i++) {
			 for(int j = 1; j <= 5; j++) {
				 mark[i][j] = 0;
			 }
		 }
    	
        for (int i=1 ; i<=5 ; i++) {
            for (int j = 1; j <= 5; j++) {
                if (i == 1) Matrix[i][j] = -1;
                else if (i == 5) Matrix[i][j] = 1;
                else Matrix[i][j] = 0;
            }
        }
        Matrix[2][1] = -1;
        Matrix[2][5] = -1;
        Matrix[3][5] = -1;
        Matrix[4][1] = 1;
        Matrix[4][5] = 1;
        Matrix[3][1] = 1;

        try {
            image = ImageIO.read(new File("Image/Ganh_Game_03_white11.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            imageRed = ImageIO.read(new File("Image/red.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            imageBlue = ImageIO.read(new File("Image/blue.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
   
        listChess.add(new Chess("red","Image/buttons_PNG30.png",2,1));
        for (int i=1; i<=5 ; i++) {
            listChess.add( new Chess("red","Image/buttons_PNG30.png",1,i));
        }
        listChess.add(new Chess("red","Image/buttons_PNG30.png",2,5));
        listChess.add(new Chess("red","Image/buttons_PNG30.png",3,5));
        listChess.add(new Chess("blue","Image/buttons_PNG36.png",3,1));
        listChess.add(new Chess("blue","Image/buttons_PNG36.png",4,1));
        for (int i = 1 ; i<=5 ; i++) {
            listChess.add(new Chess("blue","Image/buttons_PNG36.png",5,i));
        }
        listChess.add(new Chess("blue","Image/buttons_PNG36.png",4,5));
        turn = -1;
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(image,60,60,null);
        if (listChess.size()>0) {
			for (int i=0 ; i<listChess.size() ; i++) {
				listChess.get(i).draw(g2d);
			}
		}
        if (turn == -1) drawNextMove(g2d);
    }

    public void update() {
		if (turn == 1) {
			//ListChessUpdate();
			//ComputerUpdate();
			Matrix = Minimax.nextMove(Matrix, 1);
			//attackVay();
			for (int i = 1; i <= 5; i++) {
                  for (int j = 1; j <= 5; j++) {
                	  System.out.format("%3d", Matrix[i][j]);
                  }
                  System.out.println();
              }
			System.out.println();
			turn = -1;
		}
		ListChessUpdate();
    }
    
 
    public static boolean valid(int x, int y) {
		if(x < 1 || x > 5 || y < 1 || y > 5)
			return false;
		else
			return true;
    }
    

    public static boolean isCrossed(int x, int y) {
		if(x % 2 == y % 2) return true;
		return false;
    }
    
    public static void getNextMoveFrom(Chess selected) {
    	int x = selected.getX();
    	int y = selected.getY();
    	
        if(valid(x + 1, y) && Matrix[x + 1][y] == 0) listNextMove.add( new nextMove(x + 1, y));
        if(valid(x, y + 1) && Matrix[x][y + 1] == 0) listNextMove.add( new nextMove(x, y + 1));
        if(valid(x, y - 1) && Matrix[x][y - 1] == 0) listNextMove.add( new nextMove(x, y - 1));
        if(valid(x - 1, y) && Matrix[x - 1][y] == 0) listNextMove.add( new nextMove(x - 1, y));
        if(isCrossed(x, y)) {
        	 if(valid(x - 1, y + 1) && Matrix[x - 1][y + 1] == 0) listNextMove.add( new nextMove(x - 1, y + 1));
             if(valid(x + 1, y + 1) && Matrix[x + 1][y + 1] == 0 ) listNextMove.add( new nextMove(x + 1, y + 1));
             if(valid(x + 1, y - 1) && Matrix[x + 1][y - 1] == 0) listNextMove.add( new nextMove(x + 1, y - 1));
             if(valid(x - 1, y - 1) && Matrix[x - 1][y - 1] == 0) listNextMove.add( new nextMove(x - 1, y - 1));
        }
    }
    
    public static int changeInt(int x, int y) {
		return (x - 1) * 5 + y;
	}
	
	public static int changePosX(int pos) {
		if(pos % 5 == 0) return pos/5;
		return pos/5 + 1;
	}
	
	public static int changePosY(int pos) {
		if (pos % 5 != 0) return pos % 5;
		return 5;
	}
	
	public static Vector<Integer> getNeibor(int x, int y){
		Vector <Integer> vt = new Vector<Integer>();
		if(valid(x - 1, y)) vt.add(changeInt(x - 1, y));
		if(valid(x + 1, y)) vt.add(changeInt(x + 1, y));
		if(valid(x - 1, y - 1) && isCrossed(x, y)) vt.add(changeInt(x - 1, y - 1));
		if(valid(x - 1, y + 1) && isCrossed(x, y)) vt.add(changeInt(x - 1, y + 1));
		if(valid(x + 1, y + 1) && isCrossed(x, y)) vt.add(changeInt(x + 1, y + 1));
		if(valid(x + 1, y - 1) && isCrossed(x, y)) vt.add(changeInt(x + 1, y - 1));
		if(valid(x, y - 1)) vt.add(changeInt(x, y - 1));
		if(valid(x, y + 1)) vt.add(changeInt(x, y + 1));
		return vt;
	}
	
	public static Vector<Integer> getTeamNeibor(int x, int y){
		Vector <Integer> vt = new Vector<Integer>();
		if(valid(x - 1, y) && Matrix[x - 1][y] == Matrix[x][y]) vt.add(changeInt(x - 1, y));
		if(valid(x + 1, y) && Matrix[x + 1][y] == Matrix[x][y]) vt.add(changeInt(x + 1, y));
		if(valid(x - 1, y - 1) && isCrossed(x, y) && Matrix[x - 1][y - 1] == Matrix[x][y]) vt.add(changeInt(x - 1, y - 1));
		if(valid(x - 1, y + 1) && isCrossed(x, y) && Matrix[x - 1][y + 1] == Matrix[x][y]) vt.add(changeInt(x - 1, y + 1));
		if(valid(x + 1, y + 1) && isCrossed(x, y) && Matrix[x + 1][y + 1] == Matrix[x][y]) vt.add(changeInt(x + 1, y + 1));
		if(valid(x + 1, y - 1) && isCrossed(x, y) && Matrix[x + 1][y - 1] == Matrix[x][y]) vt.add(changeInt(x + 1, y - 1));
		if(valid(x, y - 1) && Matrix[x][y - 1] == Matrix[x][y]) vt.add(changeInt(x, y - 1));
		if(valid(x, y + 1) && Matrix[x][y + 1] == Matrix[x][y]) vt.add(changeInt(x, y + 1));
		return vt;
	}
	
	public static boolean check(int i, int j) {
		Vector<Integer> vt = getNeibor(i, j);
		for(int k = 0; k < vt.size(); k++) {
			int x = changePosX(vt.get(k));
			int y = changePosY(vt.get(k));
			if(Matrix[x][y] == 0) return false;
		}
		return true;
	}
	
	public static boolean checkVay(int i, int j) {
		if(check(i, j)) {
			Vector<Integer> vt = getTeamNeibor(i, j);
			mark[i][j] = 1;
			if(vt.size() == 0) return true;
			else {
				for(int k = 0; k < vt.size(); k++) {
					int x = changePosX(vt.get(k));
					int y = changePosY(vt.get(k));
					if(mark[x][y] == 1) continue;
					if(checkVay(x, y) == false) return false;
				}
				return true;
			}
		}
		return false;
	}
	
	public static void resetMark() {
		for(int i = 1; i <= 5; i++) {
			for(int j = 1; j<= 5; j++) {
				mark[i][j] = 0;
			}
		}
	}
	
	public static void attackVay() {
		for(int i = 1; i <= 5; i++) {
			for(int j = 1; j <= 5; j++) {
				resetMark();
				if(checkVay(i, j) == true) Matrix[i][j] = -Matrix[i][j];
			}
		}
	}
    public static void attack(nextMove m) {
    	int x = m.getX();
    	int y = m.getY();
    	
    	//ganh
    	if(valid(x+1, y) && valid(x-1,y) && Matrix[x + 1][y] == Matrix[x - 1][y] && Matrix[x + 1][y] != 0 && Matrix[x + 1][y] != Matrix[x][y]) {
    		Matrix[x + 1][y] = Matrix[x][y];
    		Matrix[x - 1][y] = Matrix[x][y];
    	}
    	if(valid(x, y - 1) && valid(x, y + 1) && Matrix[x][y - 1] == Matrix[x][y + 1] && Matrix[x][y - 1] != 0 && Matrix[x][y - 1] != Matrix[x][y]) {
    		Matrix[x][y - 1] = Matrix[x][y];
    		Matrix[x][y + 1] = Matrix[x][y];
    	}
    	if(isCrossed(x, y)) {
    		if(valid(x+1, y + 1) && valid(x-1, y -1) && Matrix[x + 1][y + 1] == Matrix[x - 1][y - 1] && Matrix[x + 1][y + 1] != 0 && Matrix[x + 1][y + 1] != Matrix[x][y]) {
        		Matrix[x + 1][y + 1] = Matrix[x][y];
        		Matrix[x -1][y - 1] = Matrix[x][y];
        	}
    		if(valid(x + 1, y - 1) && valid(x - 1, y + 1) && Matrix[x + 1][y - 1] == Matrix[x - 1][y + 1] && Matrix[x + 1][y - 1] != 0 && Matrix[x + 1][y - 1] != Matrix[x][y]) {
        		Matrix[x + 1][y - 1] = Matrix[x][y];
        		Matrix[x - 1][y + 1] = Matrix[x][y];
        	}
    	}
    	
    	//chet
    	if(valid(x, y + 1) && valid(x, y + 2) && Matrix[x][y + 1] == -Matrix[x][y] && Matrix[x][y + 2] == Matrix[x][y]) {
    		Matrix[x][y + 1] = Matrix[x][y];
    	}
    	
    	if(valid(x, y - 1) && valid(x, y - 2) && Matrix[x][y - 1] == -Matrix[x][y] && Matrix[x][y - 2] == Matrix[x][y]) {
    		Matrix[x][y - 1] = Matrix[x][y];
    	}
    	
    	if(valid(x - 1, y) && valid(x - 2, y) && Matrix[x - 1][y] == -Matrix[x][y] && Matrix[x - 2][y] == Matrix[x][y]) {
    		Matrix[x - 1][y] = Matrix[x][y];
    	}
    	
    	if(valid(x + 1, y) && valid(x + 2, y) && Matrix[x + 1][y] == -Matrix[x][y] && Matrix[x + 2][y] == Matrix[x][y]) {
    		Matrix[x + 1][y] = Matrix[x][y];
    	}
    	
    	if(isCrossed(x, y)) {
    		if(valid(x + 1, y + 1) && valid(x + 2, y + 2) && Matrix[x + 1][y + 1] == -Matrix[x][y] && Matrix[x + 2][y + 2] == Matrix[x][y]) {
        		Matrix[x + 1][y + 1] = Matrix[x][y];
        	}
        	
        	if(valid(x - 1, y - 1) && valid(x - 2, y - 2) && Matrix[x - 1][y - 1] == -Matrix[x][y] && Matrix[x - 2][y - 2] == Matrix[x][y]) {
        		Matrix[x - 1][y - 1] = Matrix[x][y];
        	}
        	
        	if(valid(x - 1, y + 1) && valid(x - 2, y + 2) && Matrix[x - 1][y + 1] == -Matrix[x][y] && Matrix[x - 2][y + 2] == Matrix[x][y]) {
        		Matrix[x - 1][y + 1] = Matrix[x][y];
        	}
        	
        	if(valid(x + 1, y - 1) && valid(x + 2, y - 2) && Matrix[x + 1][y - 1] == -Matrix[x][y] && Matrix[x + 2][y - 2] == Matrix[x][y]) {
        		Matrix[x + 1][y - 1] = Matrix[x][y];
        	}
    	}
    	
    	//vay
    	for(int i = 1; i <= 5; i++) {
			for(int j = 1; j <= 5; j++) {
				resetMark();
				if(checkVay(i, j) == true) Matrix[i][j] = -Matrix[i][j];
			}
		}
    }

    public static void drawNextMove(Graphics2D g2d) {
        if (listNextMove.size() > 0) {
            for (int i = 0 ; i<listNextMove.size() ; i++) {
                g2d.setColor(Color.orange);
                g2d.fillRoundRect(60 + (listNextMove.get(i).getY() - 1) * 150 - 15, 60 + (listNextMove.get(i).getX() - 1) * 150 - 15, 30, 30, 45, 45);
            }
        }
    }

    public static nextMove ComputerGetNextMove (Chess c) {
        getNextMoveFrom(c);
        if (listNextMove.size()>0) return listNextMove.get(0);
        return null;
    }

    public  void ComputerUpdate() {
		for (Chess c : listChess) {
			if (c.getTeam().equals("blue")) {
				nextMove m = ComputerGetNextMove(c);
				if (m!=null) {
					Matrix[c.getX()][c.getY()] = 0;
					Matrix[m.getX()][m.getY()] = 1;
					c.setX(m.getX());
					c.setY(m.getY());
					attack(m);
					turn = -1;
					break;
				}
			}
		}
		listNextMove.clear();
		for (int i = 1; i <= 5; i++) {
			for (int j = 1; j <= 5; j++) {
				System.out.print(Matrix[i][j] + "  ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public  void ListChessUpdate() {
		listChess.clear();
		for(int i = 1; i<=5; i++) {
			for(int j = 0; j <= 5; j++) {
				if(Matrix[i][j] == -1) {
					listChess.add(new Chess("red", imageRed, i, j));
				}
				if(Matrix[i][j] == 1) {
					listChess.add(new Chess("blue", imageBlue, i, j));
				}
			}
		}
	}

	public boolean gameOver() {
    	int dem = 0;
    	for (int i=1; i<=5 ; i++) {
    		for (int j = 1 ; j<=5 ; j++) {
    			if (Matrix[i][j]==1) ++dem;
			}
		}
    	if (dem==0 || dem==16)
    		return true;
    	return false;
	}

	public void resetGame() {
		for(int i = 1; i <= 5; i++) {
			for(int j = 1; j <= 5; j++) {
				mark[i][j] = 0;
			}
		}

		for (int i=1 ; i<=5 ; i++) {
			for (int j = 1; j <= 5; j++) {
				if (i == 1) Matrix[i][j] = -1;
				else if (i == 5) Matrix[i][j] = 1;
				else Matrix[i][j] = 0;
			}
		}
		Matrix[2][1] = -1;
		Matrix[2][5] = -1;
		Matrix[3][5] = -1;
		Matrix[4][1] = 1;
		Matrix[4][5] = 1;
		Matrix[3][1] = 1;
		turn = -1;
	}
}
