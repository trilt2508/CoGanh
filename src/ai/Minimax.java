package ai;

public class Minimax {

	public static void main(String[] args) {
		int mark[][] = { { 0, 0, 0, 0, 0, 0 }, { 0, -1, -1, -1, 0, -1 }, { 0, 0, -1, 0, 0, 0 }, { 0, 1, 1, -1, -1, -1 },
				{ 0, 0, 1, 0, 1, 0 }, { 0, 1, 0, 1, 1, 1 } };
		for (int i = 1; i <= 5; i++) {
			for (int j = 1; j <= 5; j++) {
				System.out.print(mark[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();
		mark = Minimax.nextMove(mark, -1);
		for (int i = 1; i <= 5; i++) {
			for (int j = 1; j <= 5; j++) {
				System.out.print(mark[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("------------------");
		System.out.println();
	}

	public static int[][] nextMove(int mark[][], int turn) {
		Map map = new Map();
		for (int i = 1; i <= 5; i++) {
			for (int j = 1; j <= 5; j++) {
				map.chessboard[i][j] = mark[i][j];
				if (mark[i][j] == 1) {
					map.countBlue++;
				}
				if (mark[i][j] == -1) {
					map.countRed++;
				}
			}
		}
		map.turn = turn;
		Try(0, map);
		if (map.next_move != -1)
			return map.linkMap.get(map.next_move).chessboard;
		else {
			System.out.println("het nuoc di");
			return map.chessboard;
		}
	}

	public static void Try(int k, Map m) {
		if (m.CheckFinish() != 0) {
			if (m.CheckFinish() == 1) {
				m.pointMap = 8000 - 100 * k;
			} else {
				m.pointMap = -8000 + 100 * k;
			}
			return;
		}
		if (k == 6) {
			return;
		}
		m.nextFullMap();
		for (int i = 0; i < m.linkMap.size(); i++) {
			if (m.linkMap.get(i).countBlue != m.countBlue) {
				m.an = true;
				break;
			} else
				m.an = false;
		}
		if (m.turn == 1) {
			for (int i = 0; i < m.linkMap.size(); i++) {
				Map mm = m.linkMap.get(i);
				if (m.an == false)
					Try(k + 1, mm);
				else {
					if (mm.countBlue != m.countBlue)
						Try(k + 1, mm);
					else
						continue;
				}
				if (m.anpha < mm.pointMap) {
					m.anpha = mm.pointMap;
				}
				if (k != 0)
					if (m.parent.beta <= m.anpha) {
						break;
					}
			}
			m.pointMap = m.anpha;
		} else {
			for (int i = 0; i < m.linkMap.size(); i++) {
				Map mm = m.linkMap.get(i);
				if (m.an == false)
					Try(k + 1, mm);
				else {
					if (mm.countBlue != m.countBlue)
						Try(k + 1, mm);
					else
						continue;
				}
				if (m.beta > mm.pointMap) {
					m.beta = mm.pointMap;
					// m.next_move = i;
				}
				if (k != 0)
					if (m.beta <= m.parent.anpha) {
						break;
					}
			}
			m.pointMap = m.beta;
		}
		if (k == 0) {
			if (m.turn == 1) {
				int best = -8888;
				int vt = -1;
				for (int i = 0; i < m.linkMap.size(); i++) {
					if(m.an && m.countBlue == m.linkMap.get(i).countBlue)
						continue;
					if (m.linkMap.get(i).pointMap > best) {
						best = m.linkMap.get(i).pointMap;
						vt = i;
					}
				}
				m.next_move = vt;
			} else {
				int best = 8888;
				int vt = -1;
				for (int i = 0; i < m.linkMap.size(); i++) {
					if(m.an && m.countBlue == m.linkMap.get(i).countBlue)
						continue;
					if (m.linkMap.get(i).pointMap < best) {
						best = m.linkMap.get(i).pointMap;
						vt = i;
					}
				}
				m.next_move = vt;
			}
		}
		return;
	}
}

//

//package ai;
//
//public class Minimax {
//	public static int dem;
//	public static int[][] nextMove(int mark[][], int turn) {
//		Map map = new Map();
//		for (int i = 1; i <= 5; i++) {
//			for (int j = 1; j <= 5; j++) {
//				map.chessboard[i][j] = mark[i][j];
//				if (mark[i][j] == 1) {
//					map.countBlue++;
//				}
//				if (mark[i][j] == -1) {
//					map.countRed++;
//				}
//			}
//		}
//		map.turn = turn;
//		map.nextFullMap();
//		dem = 0;
//		Try(3, map);
//		System.out.println(dem);
//		if (map.next_move != -1)
//			return map.linkMap.get(map.next_move).chessboard;
//		else {
//			System.out.println("het nuoc di");
//			return map.chessboard;
//		}
//	}
//
//	public static void Try(int k, Map m) {
//		dem++;
//		if (k == 0) {
//			return;
//		}
//		int count = -1;
//		int max = -8888;
//		int min = 8888;
//		for (int i = 0; i < m.linkMap.size(); i++) {
//			m.linkMap.get(i).nextFullMap();
//			Try(k - 1, m.linkMap.get(i));
//			if (m.turn == 1) {
//				if (m.linkMap.get(i).pointMap > max) {
//					max = m.linkMap.get(i).pointMap;
//					count = i;
//				}
//			}
//			if (m.turn == -1) {
//				if (m.linkMap.get(i).pointMap < min) {
//					min = m.linkMap.get(i).pointMap;
//					count = i;
//				}
//			}
//		}
//		if (m.turn == 1) {
//			m.pointMap = max;
//			m.next_move = count;
//		} else {
//			m.pointMap = min;
//			m.next_move = count;
//		}
//	}
//}