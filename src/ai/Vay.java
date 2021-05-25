package ai;

import java.util.Vector;

public class Vay {
    public static int[][] mark = new int[25][25];
    
    public Vay() {
    	for(int i = 1; i <= 5; i++) {
			 for(int j = 1; j <= 5; j++) {
				 mark[i][j] = 0;
			 }
		 }
    	
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
	
	public static Vector<Integer> getTeamNeibor(int x, int y, int[][] Matrix){
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
	
	public static boolean check(int i, int j, int[][] Matrix) {
		Vector<Integer> vt = getNeibor(i, j);
		for(int k = 0; k < vt.size(); k++) {
			int x = changePosX(vt.get(k));
			int y = changePosY(vt.get(k));
			if(Matrix[x][y] == 0) return false;
		}
		return true;
	}
	
	public static boolean checkVay(int i, int j, int[][] Matrix) {
		if(check(i, j, Matrix)) {
			Vector<Integer> vt = getTeamNeibor(i, j, Matrix);
			mark[i][j] = 1;
			if(vt.size() == 0) return true;
			else {
				for(int k = 0; k < vt.size(); k++) {
					int x = changePosX(vt.get(k));
					int y = changePosY(vt.get(k));
					if(mark[x][y] == 1) continue;
					if(checkVay(x, y, Matrix) == false) return false;
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
	
	public static int[][] attackVay(int[][] Matrix) {
		for(int i = 1; i <= 5; i++) {
			for(int j = 1; j <= 5; j++) {
				resetMark();
				if(checkVay(i, j, Matrix) == true) {
					Matrix[i][j] = -Matrix[i][j];
				}
				
			}
		}
		return Matrix;
	}
    
}

