package ai;

import java.util.LinkedList;


public class Map {
	public int[][] chessboard = new int[6][6];
	public int anpha = -8888;
	public int beta = 8888;
	public int countRed;
	public int countBlue;
	public int pointMap;
	public int next_move = -1;
	public Map parent;
	public boolean an;
	public static int[][] diem = { { 0, 0, 0, 0, 0, 0 }, { 0, 50, 20, 40, 20, 50 }, { 0, 20, 40, 30, 40, 20 },
			{ 0, 40, 30, 100, 30, 40 }, { 0, 20, 40, 30, 40, 20 }, { 0, 50, 20, 40, 20, 50 } };
	public LinkedList<Map> linkMap = new LinkedList<>();
	public int turn;

	public Map() {
	}

	public void creatMapNew() {
		for (int i = 1; i <= 5; i++) {
			for (int j = 1; j <= 5; j++) {
				this.chessboard[i][j] = 0;
			}
		}
		for (int j = 1; j <= 5; j++) {
			int i = 1;
			this.chessboard[i][j] = -1;
		}
		for (int j = 1; j <= 5; j++) {
			int i = 5;
			this.chessboard[i][j] = 1;
		}
		this.chessboard[2][1] = -1;
		this.chessboard[2][5] = -1;
		this.chessboard[3][5] = -1;
		this.chessboard[3][1] = 1;
		this.chessboard[4][1] = 1;
		this.chessboard[4][5] = 1;
		this.countBlue = 8;
		this.countRed = 8;
		this.turn = 1;
	}

	public void printMap() {
		for (int i = 1; i <= 5; i++) {
			for (int j = 1; j <= 5; j++) {
				System.out.format("%3d", this.chessboard[i][j]);
			}
			System.out.print("\n");
		}
	}

	public void copMap(Map map) {
		for (int i = 1; i <= 5; i++) {
			for (int j = 1; j <= 5; j++) {
				this.chessboard[i][j] = map.chessboard[i][j];
			}
		}
		this.countBlue = map.countBlue;
		this.countRed = map.countRed;
		this.turn = map.turn;
	}

	public void nextFullMap() {
		for (int i = 1; i <= 5; i++) {
			for (int j = 1; j <= 5; j++) {
				if (this.chessboard[i][j] == this.turn) {
					this.nextMap(i, j);
				}
			}
		}
	}

	public void nextMap(int i, int j) {
		if (i != 1 && this.chessboard[i - 1][j] == 0) {
			Map m = new Map();
			m.copMap(this);
			m.chessboard[i][j] = 0;
			m.chessboard[i - 1][j] = m.turn;
			this.checkMap(i - 1, j, m);
			m.turn = -m.turn;
			m.pointMap = m.heuristic_Map();
			m.parent = this;
			this.linkMap.add(m);
		}
		if (i != 5 && this.chessboard[i + 1][j] == 0) {
			Map m = new Map();
			m.copMap(this);
			m.chessboard[i][j] = 0;
			m.chessboard[i + 1][j] = m.turn;
			this.checkMap(i + 1, j, m);

			m.turn = -m.turn;
			m.pointMap = m.heuristic_Map();
			m.parent = this;
			this.linkMap.add(m);
		}
		if (j != 1 && this.chessboard[i][j - 1] == 0) {
			Map m = new Map();
			m.copMap(this);
			m.chessboard[i][j] = 0;
			m.chessboard[i][j - 1] = m.turn;
			this.checkMap(i, j - 1, m);

			m.turn = -m.turn;
			m.pointMap = m.heuristic_Map();
			m.parent = this;
			this.linkMap.add(m);
		}
		if (j != 5 && this.chessboard[i][j + 1] == 0) {
			Map m = new Map();
			m.copMap(this);
			m.chessboard[i][j] = 0;
			m.chessboard[i][j + 1] = m.turn;
			this.checkMap(i, j + 1, m);

			m.turn = -m.turn;
			m.pointMap = m.heuristic_Map();
			m.parent = this;
			this.linkMap.add(m);
		}
		if ((i + j) % 2 == 0 && i != 1) {
			if (j != 1 && this.chessboard[i - 1][j - 1] == 0) {
				Map m = new Map();
				m.copMap(this);
				m.chessboard[i][j] = 0;
				m.chessboard[i - 1][j - 1] = m.turn;
				this.checkMap(i - 1, j - 1, m);

				m.turn = -m.turn;
				m.pointMap = m.heuristic_Map();
				m.parent = this;
				this.linkMap.add(m);
			}
			if (j != 5 && this.chessboard[i - 1][j + 1] == 0) {
				Map m = new Map();
				m.copMap(this);
				m.chessboard[i][j] = 0;
				m.chessboard[i - 1][j + 1] = m.turn;
				this.checkMap(i - 1, j + 1, m);

				m.turn = -m.turn;
				m.pointMap = m.heuristic_Map();
				m.parent = this;
				this.linkMap.add(m);
			}
		}
		if ((i + j) % 2 == 0 && i != 5) {
			if (j != 1 && this.chessboard[i + 1][j - 1] == 0) {
				Map m = new Map();
				m.copMap(this);
				m.chessboard[i][j] = 0;
				m.chessboard[i + 1][j - 1] = m.turn;
				this.checkMap(i + 1, j - 1, m);

				m.turn = -m.turn;
				m.pointMap = m.heuristic_Map();
				m.parent = this;
				this.linkMap.add(m);
			}
			if (j != 5 && this.chessboard[i + 1][j + 1] == 0) {
				Map m = new Map();
				m.copMap(this);
				m.chessboard[i][j] = 0;
				m.chessboard[i + 1][j + 1] = m.turn;
				this.checkMap(i + 1, j + 1, m);

				m.turn = -m.turn;
				m.pointMap = m.heuristic_Map();
				m.parent = this;
				this.linkMap.add(m);
			}
		}
	}
	public static boolean valid(int i, int j) {
		if(i < 1 || i > 5 || j < 1 || j > 5)
			return false;
		else
			return true;
	}

	public static boolean isCrossed(int i, int j) {
		if(i % 2 == j % 2) return true;
		return false;
	}

	public void checkMap(int i, int j, Map map) {
		int k = -map.turn;
		if (j != 1 && j != 5) {
			if (map.chessboard[i][j - 1] == k && map.chessboard[i][j + 1] == k) {
				map.chessboard[i][j - 1] = map.turn;
				map.chessboard[i][j + 1] = map.turn;
				if (map.turn == 1) {
					map.countBlue += 2;
					map.countRed -= 2;
				} else {
					map.countBlue -= 2;
					map.countRed += 2;
				}
			}
		}
		if (i != 1 && i != 5) {
			if (map.chessboard[i - 1][j] == k && map.chessboard[i + 1][j] == k) {
				map.chessboard[i - 1][j] = map.turn;
				map.chessboard[i + 1][j] = map.turn;
				if (map.turn == 1) {
					map.countBlue += 2;
					map.countRed -= 2;
				} else {
					map.countBlue -= 2;
					map.countRed += 2;
				}
			}
		}
		if ((i + j) % 2 == 0 && i != 1 && i != 5 && j != 1 && j != 5) {
			if (map.chessboard[i - 1][j - 1] == k && map.chessboard[i + 1][j + 1] == k) {
				map.chessboard[i - 1][j - 1] = map.turn;
				map.chessboard[i + 1][j + 1] = map.turn;
				if (map.turn == 1) {
					map.countBlue += 2;
					map.countRed -= 2;
				} else {
					map.countBlue -= 2;
					map.countRed += 2;
				}
			}
			if (map.chessboard[i - 1][j + 1] == k && map.chessboard[i + 1][j - 1] == k) {
				map.chessboard[i - 1][j + 1] = map.turn;
				map.chessboard[i + 1][j - 1] = map.turn;
				if (map.turn == 1) {
					map.countBlue += 2;
					map.countRed -= 2;
				} else {
					map.countBlue -= 2;
					map.countRed += 2;
				}
			}
		}
		if(valid(i, j + 1) && valid(i, j + 2) && map.chessboard[i][j + 1] == -map.turn && map.chessboard[i][j + 2] == map.turn) {
			map.chessboard[i][j + 1] = map.turn;
			if (map.turn == 1) {
				map.countBlue += 1;
				map.countRed -= 1;
			} else {
				map.countBlue -= 1;
				map.countRed += 1;
			}
		}

		if(valid(i, j - 1) && valid(i, j - 2) && map.chessboard[i][j - 1] == -map.turn && map.chessboard[i][j - 2] == map.turn) {
			map.chessboard[i][j - 1] = map.turn;
			if (map.turn == 1) {
				map.countBlue += 1;
				map.countRed -= 1;
			} else {
				map.countBlue -= 1;
				map.countRed += 1;
			}
		}

		if(valid(i - 1, j) && valid(i - 2, j) && map.chessboard[i - 1][j] == -map.turn && map.chessboard[i - 2][j] == map.turn) {
			map.chessboard[i - 1][j] = map.turn;
			if (map.turn == 1) {
				map.countBlue += 1;
				map.countRed -= 1;
			} else {
				map.countBlue -= 1;
				map.countRed += 1;
			}
		}

		if(valid(i + 1, j) && valid(i + 2, j) && map.chessboard[i + 1][j] == -map.turn && map.chessboard[i + 2][j] == map.turn) {
			map.chessboard[i + 1][j] = map.turn;
			if (map.turn == 1) {
				map.countBlue += 1;
				map.countRed -= 1;
			} else {
				map.countBlue -= 1;
				map.countRed += 1;
			}
		}

		if(isCrossed(i, j)) {
			if(valid(i + 1, j + 1) && valid(i + 2, j + 2) && map.chessboard[i + 1][j + 1] == -map.turn && map.chessboard[i + 2][j + 2] == map.turn) {
				map.chessboard[i + 1][j + 1] = map.turn;
				if (map.turn == 1) {
					map.countBlue += 1;
					map.countRed -= 1;
				} else {
					map.countBlue -= 1;
					map.countRed += 1;
				}
			}

			if(valid(i - 1, j - 1) && valid(i - 2, j - 2) && map.chessboard[i - 1][j - 1] == -map.turn && map.chessboard[i - 2][j - 2] == map.turn) {
				map.chessboard[i - 1][j - 1] = map.turn;
				if (map.turn == 1) {
					map.countBlue += 1;
					map.countRed -= 1;
				} else {
					map.countBlue -= 1;
					map.countRed += 1;
				}
			}

			if(valid(i - 1, j + 1) && valid(i - 2, j + 2) && map.chessboard[i - 1][j + 1] == -map.turn && map.chessboard[i - 2][j + 2] == map.turn) {
				map.chessboard[i - 1][j + 1] = map.turn;
				if (map.turn == 1) {
					map.countBlue += 1;
					map.countRed -= 1;
				} else {
					map.countBlue -= 1;
					map.countRed += 1;
				}
			}

			if(valid(i + 1, j - 1) && valid(i + 2, j - 2) && map.chessboard[i + 1][j - 1] == -map.turn && map.chessboard[i + 2][j - 2] == map.turn) {
				map.chessboard[i + 1][j - 1] = map.turn;
				if (map.turn == 1) {
					map.countBlue += 1;
					map.countRed -= 1;
				} else {
					map.countBlue -= 1;
					map.countRed += 1;
				}
			}
		}

		map.chessboard = Vay.attackVay(map.chessboard);
		int blue = 0;
		int red = 0;
		for(int a = 1; a <= 5; a++) {
			for(int b = 1; b <= 5; b++) {
				if(map.chessboard[a][b] == 1 ) blue++;
				else if (map.chessboard[a][b] == -1) red++;
			}
		}
		map.countBlue = blue;
		map.countRed = red;

	}

	public int heuristic_Map() {
		int S = 0;
		//S = 100 * (this.countBlue - this.countRed);
		for (int i = 1; i <= 5; i++) {
			for (int j = 1; j <= 5; j++) {
				if (this.chessboard[i][j] == 1) {
					S += diem[i][j];
				}
				if (this.chessboard[i][j] == -1) {
					S -= diem[i][j];
				}
			}
		}
		S += this.linkMap.size() * 5 * this.turn;
		return S;
	}

	public int CheckFinish() {
		if (this.countRed == 0) {
			return 1;
		}
		if (this.countBlue == 0) {
			return -1;
		}
		return 0;
	}

}

