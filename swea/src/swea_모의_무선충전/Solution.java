package swea_모의_무선충전;

import java.util.*;
import java.io.*;

public class Solution {

	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("src/swea_모의_무선충전/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int T = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());
			M = Integer.parseInt(st.nextToken()); // 이동시간
			A = Integer.parseInt(st.nextToken()); // BC의 개수
			ans = 0;
			people = new ArrayList[2]; // 사람 2명
			for (int i = 0; i < 2; i++) {
				people[i] = new ArrayList<>();
			}
			// 초기위치 추가
			people[0].add(new Node(0, 0));
			people[1].add(new Node(9, 9));
			// 이동 입력받기
			for (int i = 0; i < 2; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < M; j++) {
					int dir = Integer.parseInt(st.nextToken());
					int cx = people[i].get(j).x + dx[dir];
					int cy = people[i].get(j).y + dy[dir];
					people[i].add(new Node(cx, cy));
				}
				// System.out.println(people[i]);
			}

			// bc 입력받기
			bc = new ArrayList<>();
			for (int i = 0; i < A; i++) {
				st = new StringTokenizer(br.readLine());
				int[] tmp = new int[4];
				for (int j = 0; j < 4; j++) {
					tmp[j] = Integer.parseInt(st.nextToken());
				}
				bc.add(new BC(tmp[1] - 1, tmp[0] - 1, tmp[2], tmp[3]));
			}
			// System.out.println(ap);
			dfs(0, 0);
			System.out.println("#" + tc + " " + ans);
		}
	}

	static void dfs(int time, int sum) {
		// System.out.println("시간" + time);
		if (time == M + 1) {
			ans = sum;
			return;
		}
		// 현재 위치의 사람한테 몇개의 bc가 있는지 체크하는 순서
		int cntA = 0;
		int cntB = 0;
		// [사람][bc의 개수]
		int[][] visit = new int[2][A];
		// bc의 개수만큼 돌면서
		// 현재 시간의 사람의 위치와 bc의 위치 거리를 계산해 범위 안에 있으면
		// 방문했다고 ++ 체크 해주기
		// [1, 0, 1] 사람A
		// [1, 0, 0] 사람B
		for (int i = 0; i < A; i++) {
			int distA = Math.abs(bc.get(i).x - people[0].get(time).x) + Math.abs(bc.get(i).y - people[0].get(time).y);
			if (distA <= bc.get(i).size) {
				visit[0][i]++;
				cntA++;
			}
			int distB = Math.abs(bc.get(i).x - people[1].get(time).x) + Math.abs(bc.get(i).y - people[1].get(time).y);
			if (distB <= bc.get(i).size) {
				visit[1][i]++;
				cntB++;
			}
		}
		for (int i = 0; i < 2; i++) {
			System.out.println(Arrays.toString(visit[i]));
		}
		System.out.println(cntA + " " + cntB);
		List<Node> select = new ArrayList<>();
		if (cntA == 0 || cntB == 0) {
			int cal = 0;
			if (cntA == 0) {
				for (int i = 0; i < A; i++) {
					int tmp = 0;
					if (visit[1][i] == 1) {
						cal = Math.max(cal, bc.get(i).P);
					}
				}
			}
			if (cntB == 0) {
				for (int i = 0; i < A; i++) {
					int tmp = 0;
					if (visit[0][i] == 1) {
						cal = Math.max(cal, bc.get(i).P);
					}
				}
			}
			// System.out.println(cal + " 계산");
			dfs(time + 1, sum + cal);
		} else {// 이거 여러개 겹친경우
			for (int i = 0; i < A; i++) {
				for (int j = 0; j < A; j++) {
					if (visit[0][i] + visit[1][j] == 2) {
						boolean flag = false;
						if (i == j) {
							flag = true;
						}
						select.add(new Node(i, j, flag));
					}
				}
			}

			int cal = 0;
			for (int i = 0; i < select.size(); i++) {
				int tmp = 0;
				tmp += bc.get(select.get(i).x).P;// 첫번째사람
				tmp += bc.get(select.get(i).y).P;// 두번째사람
				if (select.get(i).same) {
					tmp = tmp / 2;
				}
				cal = Math.max(cal, tmp);
			}
			// System.out.println(cal + " 계산");
			// System.out.println(select);
			dfs(time + 1, sum + cal);
		}
	}

	static int ans, M, A;
	static List<BC> bc;
	static List<Node>[] people;

	static int calD(int x1, int x2, int y1, int y2) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);

	}

	static int[] dx = { 0, -1, 0, 1, 0 };// 위 오른 아래 왼쪽
	static int[] dy = { 0, 0, 1, 0, -1 };

	static class Node {
		int x;
		int y;
		boolean same;

		public Node(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		public Node(int x, int y, boolean same) {
			super();
			this.x = x;
			this.y = y;
			this.same = same;
		}

		@Override
		public String toString() {
			return "Node [x=" + x + ", y=" + y + ", same=" + same + "]\n";
		}

	}

	static class BC {
		int x;
		int y;
		int size;
		int P;

		@Override
		public String toString() {
			return "BC [x=" + x + ", y=" + y + ", size=" + size + ", P=" + P + "]\n";
		}

		public BC(int x, int y, int c, int p) {
			super();
			this.x = x;
			this.y = y;
			size = c;
			P = p;
		}

	}
}
