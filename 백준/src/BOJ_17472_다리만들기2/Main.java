package BOJ_17472_다리만들기2;

import java.util.*;
import java.io.*;

public class Main {
	static int R, C, island;
	static int[][] map;
	static int ans = 0;
	static int[] dx = { 0, 0, 1, -1 };
	static int[] dy = { 1, -1, 0, 0 };

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("src/BOJ_17472_다리만들기2/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		map = new int[R][C];
		for (int i = 0; i < R; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < C; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		boolean[][] visit = new boolean[R][C];
		island = 0;
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (map[i][j] != 0 && !visit[i][j]) {
					visit[i][j] = true;
					redesign(i, j, ++island, visit);
				}
			}
		}
		// System.out.println("-------------");
		// 섬에 번호 붙이기
		// for (int i = 0; i < R; i++) {
		// System.out.println(Arrays.toString(map[i]));
		// }
		// System.out.println(island);
		// System.out.println("-------------");

		// 인접행렬만들기
		int[][] edge = new int[island + 1][island + 1];
		for (int i = 0; i <= island; i++) {
			for (int j = 0; j <= island; j++) {
				if (i == j) {
					continue;
				}
				edge[i][j] = Integer.MAX_VALUE;
			}
		}
		// System.out.println("-------------");
		for (int i = 0; i < R; i++) {
			for (int j = 0; j < C; j++) {
				if (map[i][j] != 0) {
					find_node(edge, i, j);
				}
			}
		}

		// for (int i = 1; i <= island; i++) {
		// System.out.println(Arrays.toString(edge[i]));
		// }
		prim(edge);

		if (ans == 0) {
			System.out.println(-1);
		} else {
			System.out.println(ans);
		}
	}

	static void prim(int[][] edge) {
		int start = 1;
		boolean[] check = new boolean[island + 1];
		List<Integer> visit = new ArrayList<>(); // 방문한 곳 리스트
		visit.add(start);
		check[start] = true;
		int min_island = -1;
		int min_len = Integer.MAX_VALUE;
		for (int i = 1; i < island; i++) {// 섬-1 만큼 돌기
			min_len = Integer.MAX_VALUE;
			for (int j = 0; j < visit.size(); j++) { // 방문 했던 애들 돌기
				int tmp_start = visit.get(j);
				for (int k = 1; k <= island; k++) { // 방문 한 곳이랑 연결된 곳 돌기
					if (k == tmp_start || check[k]) {
						continue;
					}
					if (edge[tmp_start][k] < min_len) {
						min_len = edge[tmp_start][k];
						min_island = k;
						start = k;
					}
				}
			}
			// System.out.println("짧은 선길이 " + min_len + " 연결된 섬 " + min_island + " 시작섬" +
			// start);
			if (min_island != -1 && min_len != Integer.MAX_VALUE) {
				ans += min_len;
				check[min_island] = true;
				visit.add(min_island);
				// System.out.println(visit);
			}
		}
		if (island != visit.size()) {
			ans = 0;
		}
	}

	static void find_node(int[][] edge, int x, int y) {
		Queue<Node> q = new LinkedList<>();
		int start_island = map[x][y];
		q.add(new Node(x, y, start_island, 0, 0));
		q.add(new Node(x, y, start_island, 0, 1));
		q.add(new Node(x, y, start_island, 0, 2));
		q.add(new Node(x, y, start_island, 0, 3));
		boolean[][] visit = new boolean[R][C];
		visit[x][y] = true;
		while (!q.isEmpty()) {
			Node tmp = q.poll();
			int cx = tmp.x + dx[tmp.dir];
			int cy = tmp.y + dy[tmp.dir];
			if (cx < 0 || cy < 0 || cx >= R || cy >= C || visit[cx][cy]) {
				continue;
			}

			visit[cx][cy] = true;
			if (map[cx][cy] == 0) {// 바다
				q.add(new Node(cx, cy, start_island, tmp.len + 1, tmp.dir));
			} else if (map[cx][cy] != tmp.num) {// 출발한 섬 번호랑 다르다
				if (edge[tmp.num][map[cx][cy]] > tmp.len && tmp.len >= 2) {
					edge[tmp.num][map[cx][cy]] = tmp.len;
				}
			}
		}
	}

	static void redesign(int x, int y, int num, boolean[][] visit) {
		Queue<Node> q = new LinkedList<>();
		q.add(new Node(x, y, island));

		while (!q.isEmpty()) {
			Node tmp = q.poll();
			map[tmp.x][tmp.y] = tmp.num;
			for (int i = 0; i < 4; i++) {
				int cx = tmp.x + dx[i];
				int cy = tmp.y + dy[i];

				if (cx >= R || cy >= C || cx < 0 || cy < 0 || visit[cx][cy]) {
					continue;
				}
				if (map[cx][cy] != 0) {
					visit[cx][cy] = true;
					q.add(new Node(cx, cy, tmp.num));
				}
			}
		}

	}

	static class Node {
		int x;
		int y;
		int num;
		int len;
		int dir;

		@Override
		public String toString() {
			return "Node [x=" + x + ", y=" + y + ", num=" + num + ", len=" + len + ", dir=" + dir + "]\n";
		}

		public Node(int num, int len) {// 인접리스트용
			super();
			this.num = num;
			this.len = len;
		}

		public Node(int x, int y, int num) { // redesign 용
			super();
			this.x = x;
			this.y = y;
			this.num = num;
		}

		public Node(int x, int y, int num, int len, int dir) { //
			super();
			this.x = x;
			this.y = y;
			this.num = num;
			this.len = len;
			this.dir = dir;
		}

	}

	// 더럽지만 프림 알고리즘으로 풀었음
	// 인접리스트와 인접 행렬중에서 많은 고민을 했으나 인접 행렬로 함
	// 덕분에 삼중for문 헷갈렸다.
	
	// 1%에서 틀린이유 1:
	// 108~110 코드를 추가안해서 틀림!
	// input.txt의 경우 맨오른쪽위의 섬 하나가 연결안되어있음!
	// 그래서 마지막에 연결된 섬의 수랑 원래 섬의 수랑 다르면 -1로 출력되게 고침
}
