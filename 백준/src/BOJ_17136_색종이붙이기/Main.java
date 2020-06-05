package BOJ_17136_색종이붙이기;

//효율 최악이지만 통과는 했다....
//884ms라니..	
import java.util.*;
import java.io.*;

public class Main {

	static int ans = Integer.MAX_VALUE;

	static class Node {
		int x;
		int y;

		public Node(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "Node [x=" + x + ", y=" + y + "]\n";
		}
	}

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("src/BOJ_17136_색종이붙이기/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int left = 0;
		int[][] map = new int[10][10];
		for (int i = 0; i < 10; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 10; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == 1) {
					left++;
				}
			}
		}
		int[] paper = { 0, 5, 5, 5, 5, 5 };
		dfs(map, paper, left, 0);
		if (ans == Integer.MAX_VALUE) {
			System.out.println(-1);
		} else {
			System.out.println(ans);
		}
	}

	static void dfs(int[][] map, int[] paper, int left, int use) {
		if (ans < use) {
			return;
		}
		if (left == 0) {
			// System.out.println("남은 갯수" + left + " 사용갯수" + use);
			if (ans > use) {
				ans = use;
			}
			return;
		}
		// 시작 점 찾고
		List<Node> list = new ArrayList<>();
		next: for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (map[i][j] == 1 && find_edge(map, i, j)) {
					list.add(new Node(i, j));
					break next;
				}
			}
		}
		if (list.size() == 0) {
			// 안에 뭐 없다 끝!
			return;
		}
		Node cur = list.get(0);
		for (int s = 5; s > 0; s--) {
			if (paper[s] < 1) {
				continue;
			}
			if (is_ok(map, cur.x, cur.y, s)) {
				// 이 사이즈가 들어간다면
				int[][] tmp = copy_map(map);
				change_map(tmp, cur.x, cur.y, s);// 지우고
				// 넘기고!
				paper[s]--;
				dfs(tmp, paper, left - s * s, use + 1);
				paper[s]++;
			}
		}
		// }

	}

	static boolean find_edge(int[][] arr, int x, int y) {
		int cx, cy;
		for (int i = -1; i <= 0; i++) {
			for (int j = -1; j <= 0; j++) {
				if (i == 0 && j == 0) {
					continue;
				}
				cx = x + i;
				cy = j + y;
				if (cx < 0 || cy < 0) {
					continue;
				}
				if (arr[cx][cy] == 1) {
					return false;
				}
			}
		}
		return true;
	}

	static int[][] copy_map(int[][] map) {// 맵 전체 복사하기
		int[][] tmp = new int[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				tmp[i][j] = map[i][j];
			}
		}
		return tmp;
	}

	static void change_map(int[][] map, int x, int y, int size) { // 맵에서 지우기
		int cx, cy;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				cx = x + i;
				cy = y + j;
				map[cx][cy] = 0;
			}
		}
	}

	static boolean is_ok(int[][] map, int x, int y, int size) { // 시작점, 크기
		int cx, cy;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				cx = x + i;
				cy = y + j;
				if (cx >= 10 || cy >= 10 || map[cx][cy] != 1) {
					return false;
				}
			}
		}
		return true;
	}
}
