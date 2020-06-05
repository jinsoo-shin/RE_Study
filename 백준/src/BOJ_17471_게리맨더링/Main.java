package BOJ_17471_게리맨더링;

import java.util.*;
import java.io.*;

public class Main {

	static int N;
	static int[][] edge;
	static int[] people;
	static int ans = Integer.MAX_VALUE;

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("src/BOJ_17471_게리맨더링/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		N = Integer.parseInt(br.readLine());
		people = new int[N + 1];
		edge = new int[N + 1][N + 1];
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= N; i++) {
			people[i] = Integer.parseInt(st.nextToken());
		}

		int zero_cnt = 0;
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			int tmp = Integer.parseInt(st.nextToken());
			if (tmp == 0) {
				zero_cnt++;
			} else {
				while (st.hasMoreTokens()) {
					tmp = Integer.parseInt(st.nextToken());
					edge[i][tmp] = 1;
				}
			}
			// System.out.println(Arrays.toString(edge[i]));
		}

		if (zero_cnt >= 2 && N != 2) {// 구역을 2군데로 나눌 수 없는 경우
			System.out.println(-1);
		} else if (N == 2) {// 바로 2군데로 나눌 수 있음
			System.out.println(Math.abs(people[1] - people[2]));
		} else {
			for (int i = 1; i <= N / 2; i++) {
				nCr(new boolean[N], i, 0, 0);
			}
		}
		if (ans == Integer.MAX_VALUE) {
			ans = -1;
		} else {
			System.out.println(ans);
		}
		// System.out.println(isConnect(new int[] { 1, 3, 4 }));
	}

	static boolean isConnect(int[] arr) {
		if (arr.length == 1) {
			return true;
		} else {
			// 연결되어있는지 체크하기
			List<Integer> visit = new ArrayList<>();
			visit.add(arr[0]);

			boolean[] check = new boolean[arr.length];
			check[0] = true;
			// 일단 연결될 점은 확실하고
			// 이미 합쳐진 애들이랑 연결 되어있는지 체크체크
			next: for (int i = 1; i < arr.length; i++) {
				for (int j = 0; j < visit.size(); j++) {
					int start = visit.get(j);
					for (int k = 0; k < arr.length; k++) {
						int end = arr[k];
						if (start == end) {
							continue;
						}
						if (!check[k] && edge[start][end] == 1) {
							visit.add(end);
							check[k] = true;
							continue next;
						}
					}
				}
			}

			// System.out.println(visit);
			if (visit.size() == arr.length) {
				return true;
			} else {
				return false;
			}
		}
	}

	static void calc(int[] A, int[] B) {
		int sumA = 0;
		int sumB = 0;
		if (!isConnect(A) || !isConnect(B)) {
			return;
		}
		for (int i = 0; i < A.length; i++) {
			sumA += people[A[i]];
		}
		for (int j = 0; j < B.length; j++) {
			sumB += people[B[j]];
		}

		int tmp = Math.abs(sumA - sumB);
		// System.out.println("결과 " + tmp);
		if (ans > tmp) {
			ans = tmp;
		}
	}

	static void nCr(boolean[] flag, int r, int idx, int cnt) {
		if (cnt > r) {
			return;
		}
		if (idx == N) {
			if (cnt == r) {
				int[] arr1 = new int[r];
				int[] arr2 = new int[N - r];
				int a1 = 0;
				int a2 = 0;
				for (int i = 0; i < N; i++) {
					if (flag[i]) {
						arr1[a1++] = i + 1;
					} else {
						arr2[a2++] = i + 1;
					}
				}
				// System.out.println("----------------");
				// System.out.println(Arrays.toString(arr1));
				// System.out.println(Arrays.toString(arr2));
				calc(arr1, arr2);
			}
			return;
		}
		flag[idx] = true;
		nCr(flag, r, idx + 1, cnt + 1);
		flag[idx] = false;
		nCr(flag, r, idx + 1, cnt);

	}
}
// isConnect 함수가 제일 어려웠다....
// 조합 + 완탐 으로 풀었음
// 88ms 걸림
// 풀이 80분 걸림...