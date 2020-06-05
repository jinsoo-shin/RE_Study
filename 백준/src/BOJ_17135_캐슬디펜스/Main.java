package BOJ_17135_캐슬디펜스;

import java.util.*;
import java.io.*;

public class Main {
	static int R, C, D;
	static int[][] map;
	static List<Node> enemy;
	static int ans = 0;

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("src/BOJ_17135_캐슬디펜스/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());
		map = new int[R][C];
		enemy = new ArrayList<>();

		for (int i = 0; i < R; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < C; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == 1) {
					// 적의 좌표 미리 받아놓기
					enemy.add(new Node(i, j));
				}
			}
		}

		// 궁수 시작 위치별로 적까지의 거리 계산해두기
		archer = new ArrayList[C];
		for (int i = 0; i < C; i++) {
			archer[i] = new ArrayList<>();
		}
		calc_range();
		// 가깝고, 가장 왼쪽의 적부터 정렬하기
		for (int i = 0; i < archer.length; i++) {
			Collections.sort(archer[i]);
		}
		// 궁수 조합 찾아놓기
		archer_place(new boolean[C], 0, 0);
		System.out.println(ans);
	}

	static List<Node> copy(int num) {
		List<Node> tmp = new ArrayList<>();
		for (int i = 0; i < archer[num].size(); i++) {
			tmp.add(archer[num].get(i));
		}
		return tmp;
	}

	static void simul(int[] arr) {
		boolean[][] dead = new boolean[R][C]; // 죽은 적
		List<Node>[] p = new ArrayList[3];
		for (int i = 0; i < 3; i++) {
			p[i] = copy(arr[i]);
		}

		for (int turn = 0; turn < R; turn++) {
			List<Node> dead_enemy = new ArrayList<>();
			next: for (int a = 0; a < 3; a++) {// 궁수
				for (int s = 0; s < p[a].size(); s++) {
					Node tmp = p[a].get(s);
					// System.out.println("turn:" + turn + " 궁수번호:" + s + " 현재 적:" + tmp);
					if (tmp.x < (R - turn)) {
						// 한칸씩 당겨지는거 범위 안에있는지 체크하기
						if (tmp.len - turn <= D) {// 사정거리 안에있으면
							dead[tmp.x][tmp.y] = true;
							dead_enemy.add(new Node(tmp.x, tmp.y));
							continue next;
						}
					}
				}
			}
			// 턴종료함
			for (int i = 0; i < dead_enemy.size(); i++) {
				Node tmp_dead = dead_enemy.get(i);
				next: for (int j = 0; j < 3; j++) { // 궁수
					for (int k = 0; k < p[j].size(); k++) {// 탐색탐색
						Node tmp = p[j].get(k);
						if (tmp.x == tmp_dead.x && tmp.y == tmp_dead.y) {
							p[j].remove(k);
							k--;
							if (k < 0)
								k = 0;
						}
					}
				}
			}
		}

		int cnt = 0;
		for (int i = 0; i < enemy.size(); i++) {
			Node tmp = enemy.get(i);
			if (dead[tmp.x][tmp.y]) {
				cnt++;
			}
		}
		if (ans < cnt) {
			ans = cnt;
		}
	}

	static List<Node> archer[];

	static int dist(int x1, int y1, int x2, int y2) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}

	static void calc_range() {
		int x = R;
		for (int y = 0; y < C; y++) {
			for (int i = 0; i < enemy.size(); i++) {
				Node tmp = enemy.get(i);
				int tmp_dist = dist(tmp.x, tmp.y, x, y);
				archer[y].add(new Node(tmp.x, tmp.y, tmp_dist));
			}
		}
	}

	// 궁수 배치
	static void archer_place(boolean[] flag, int idx, int cnt) {
		if (cnt > 3) {
			return;
		}
		if (idx == flag.length) {
			if (cnt == 3) {
				int[] arr = new int[3];
				int ii = 0;
				for (int i = 0; i < flag.length; i++) {
					if (flag[i]) {
						arr[ii++] = i;
					}
				}
				// System.out.println(Arrays.toString(arr));
				simul(arr);
			}

			return;
		}

		flag[idx] = true;
		archer_place(flag, idx + 1, cnt + 1);
		flag[idx] = false;
		archer_place(flag, idx + 1, cnt);
	}

	static class Node implements Comparable<Node> {
		int x;
		int y;
		int len;

		public Node(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		public Node(int x, int y, int len) {
			super();
			this.x = x;
			this.y = y;
			this.len = len;
		}

		@Override
		public String toString() {
			return "Node [x=" + x + ", y=" + y + ", len=" + len + "]\n";
		}

		@Override // 거리가 짧으면서 왼쪽y우선
		public int compareTo(Node o) {
			if (this.len == o.len) {
				return this.y - o.y;
			} else {
				return this.len - o.len;
			}
		}

	}
}

// 틀린이유:
// 턴종료하고 다른데에 죽은 적을 처리해준게아니라
// 턴 돌면서 궁수 한명꺼 리스트에서만 삭제해서
// 다음 턴에서 다른 궁수가 또 죽여서 문제였음
// 같은 턴에서 같이 죽이는 건 상관없는데
// 다른 턴에서 또 죽이는 건 문제가 됨!
