package BOJ_4179_불;

import java.util.*;
import java.io.*;

public class Main {
	static int R, C;
	static int ans = Integer.MAX_VALUE;
	static String[][] map;
	static int[] dx = { 0, 0, 1, -1 };
	static int[] dy = { 1, -1, 0, 0 };

	static class Node {
		int x;
		int y;
		int time;
		boolean isFire;

		public Node(int x, int y, int time, boolean isJ) {
			super();
			this.x = x;
			this.y = y;
			this.time = time;
			this.isFire = isJ;
		}

		@Override
		public String toString() {
			return "Node [x=" + x + ", y=" + y + ", time=" + time + ", isFire=" + isFire + "]\n";
		}

	}

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("src/BOJ_4179_불/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		map = new String[R][C];
		Queue<Node> q = new LinkedList<>();
		int[] tmpJ = new int[2];
		for (int i = 0; i < R; i++) {
			String[] tmp = br.readLine().split("");
			for (int j = 0; j < C; j++) {
				map[i][j] = tmp[j];
				if (map[i][j].equals("J")) {
					tmpJ[0] = i;
					tmpJ[1] = j;
				} else if (map[i][j].equals("F")) {
					q.add(new Node(i, j, 1, true));
				}
			}
			// System.out.println(Arrays.toString(map[i]));
		}
		q.add(new Node(tmpJ[0], tmpJ[1], 1, false));
		bfs(q);

		if (ans == Integer.MAX_VALUE) {
			System.out.println("IMPOSSIBLE");
		} else {
			System.out.println(ans);
		}
	}

	static void bfs(Queue<Node> q) {

		while (!q.isEmpty()) {
			Node tmp = q.poll();
			for (int i = 0; i < 4; i++) {
				int cx = tmp.x + dx[i];
				int cy = tmp.y + dy[i];
				if (cx < 0 || cy < 0 || cx >= R || cy >= C) {
					if (!tmp.isFire && ans > tmp.time) {
						ans = tmp.time;
					}
					continue;
				}
				if (tmp.isFire && map[cx][cy].equals(".")) {
					q.add(new Node(cx, cy, tmp.time + 1, true));
					map[cx][cy] = "F";
				} else if (map[cx][cy].equals(".")) {
					q.add(new Node(cx, cy, tmp.time + 1, false));
					map[cx][cy] = "J";
				}
			}
		}
	}
}

// 7%에서 틀림

// 이유: 시간 체크를 잘못했음....
// 예제 테케에서 답이 3이어야하는데 2로 나오게 코드를 작성함...
// 시작 시간을 1로 두는 것으로 변경함
