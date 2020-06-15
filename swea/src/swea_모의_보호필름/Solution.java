package swea_모의_보호필름;

import java.io.*;
import java.util.*;

public class Solution {
	public static void main(String[] args) throws Exception {
		System.setIn(new FileInputStream("src/swea_모의_보호필름/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int T = Integer.parseInt(br.readLine().trim());
		for (int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine().trim());
			R = Integer.parseInt(st.nextToken());// 두께D
			C = Integer.parseInt(st.nextToken());// 가로W
			K = Integer.parseInt(st.nextToken());// 합격기준
			ans = 987654321;
			int[][] map = new int[R][C];
			for (int i = 0; i < R; i++) {
				st = new StringTokenizer(br.readLine().trim());
				for (int j = 0; j < C; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			dfs(map, 0, 0);
			System.out.println("#" + tc + " " + ans);
		}
	}

	static void dfs(int[][] map, int idx, int cnt) {
		if (cnt > ans) {
			return;
		}
		if (check(map)) {
			ans = Math.min(ans, cnt);
			return;
		}
		if (idx == R) {
			if (check(map)) {
				ans = Math.min(ans, cnt);
			}
			return;
		}
		int[] origin = new int[C];
		origin = Arrays.copyOfRange(map[idx], 0, C);
		Arrays.fill(map[idx], 0);
		dfs(map, idx + 1, cnt + 1);
		Arrays.fill(map[idx], 1);
		dfs(map, idx + 1, cnt + 1);
		map[idx] = Arrays.copyOfRange(origin, 0, C);
		dfs(map, idx + 1, cnt);

	}

	static boolean check(int[][] map) {

		next: for (int j = 0; j < C; j++) {
			int cnt = 1;
			for (int i = 1; i < R; i++) {
				if (map[i - 1][j] == map[i][j]) {
					cnt++;
				} else {
					cnt = 1;
				}
				if (cnt == K) {// 연속으로 무언가가 K개가 나왔으면 그 줄은 통과
					continue next;
				}
			}
			if (cnt < K) {
				return false;
			}
		}
		return true;
	}

	static int ans, R, C, K;
}
