package BOJ_2146_다리만들기;

import java.util.*;
import java.io.*;

public class Main {
	static int N;
	static int[][] map;
	static int[] dx = { 1, -1, 0, 0 };
	static int[] dy = { 0, 0, 1, -1 };
	static int ans;

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("src/BOJ_2146_다리만들기/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		StringTokenizer st;
		map = new int[N][N];
		ans = Integer.MAX_VALUE;
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
			// System.out.println(Arrays.toString(map[i]));
		}
		// System.out.println("----------------");
		// 섬에 번호를 붙이기
		int mapnum = 2;
		boolean[][] visit = new boolean[N][N];

		boolean[][] edge = new boolean[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (map[i][j] != 0 && !visit[i][j]) {
					visit[i][j] = true;
					redesign_map(i, j, mapnum, visit, edge);
					mapnum++;
				}
			}
		}
		// 다리 연결하기
		// System.out.println("-------------");
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if (edge[i][j]) {
					find_bridge(i, j);
				}
			}
		}
		System.out.println(ans);
	}

	static void redesign_map(int x, int y, int mapnum, boolean[][] visit, boolean[][] edge) {
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] { x, y });

		while (!q.isEmpty()) {
			int[] tmp = q.poll();
			map[tmp[0]][tmp[1]] = mapnum;
			for (int i = 0; i < 4; i++) {
				int cx = tmp[0] + dx[i];
				int cy = tmp[1] + dy[i];
				if (cx >= N || cy >= N || cx < 0 || cy < 0 || visit[cx][cy]) {
					continue;
				}
				if (map[cx][cy] != 0) {
					visit[cx][cy] = true;
					q.add(new int[] { cx, cy });
				} else if (map[cx][cy] == 0) {
					edge[tmp[0]][tmp[1]] = true;
				}
			}
		}
	}

	static void find_bridge(int x, int y) {
		boolean[][] visit = new boolean[N][N];
		Queue<Node> q = new LinkedList<>();
		q.add(new Node(x, y, 0, map[x][y]));
		visit[x][y] = true;

		while (!q.isEmpty()) {
			Node tmp = q.poll();

			for (int i = 0; i < 4; i++) {
				int cx = tmp.x + dx[i];
				int cy = tmp.y + dy[i];
				if (cx < 0 || cy < 0 || cx >= N || cy >= N || visit[cx][cy]) {
					continue;
				}

				if (map[cx][cy] == 0) {
					q.add(new Node(cx, cy, tmp.len + 1, tmp.num));
					visit[cx][cy] = true;
				} else if (map[cx][cy] != tmp.num) {
					if (ans > tmp.len) {
						ans = tmp.len;
					}
				}
			}
		}

	}

	static class Node {
		int x;
		int y;
		int len;
		int num;

		public Node(int x, int y, int len, int num) {
			super();
			this.x = x;
			this.y = y;
			this.len = len;
			this.num = num;
		}

		@Override
		public String toString() {
			return "Node [x=" + x + ", y=" + y + ", len=" + len + ", num=" + num + "]\n";
		}

	}
}

// 42%에서 틀림 ->dfs로 풀었을때
// 정답 => bfs로 변경
// 더 빠르게 하려면 거리를 구할때 bfs보다 math.abs로 점과 점 사이를 구하는것이 빠르다
// 더 빠른 답안 : https://www.acmicpc.net/source/20167138
