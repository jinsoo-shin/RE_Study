package Baekjoon_17822_원판돌리기;

import java.io.*;
import java.util.*;

public class Main {
	static int N, M, T;
	static int[][] arr;
	static int x, d, k;
	static int sum, cnt;
	static boolean flag;

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("src/Baekjoon_17822_원판돌리기/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 원판 반지름
		M = Integer.parseInt(st.nextToken()); // 원판 안의 숫자
		T = Integer.parseInt(st.nextToken()); // 명령어 갯수
		arr = new int[N][M]; // 원판
		sum = 0;// 초기화
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
				sum += arr[i][j];
				cnt++;
			}
			// System.out.println(Arrays.toString(arr[i]));
		}
		// System.out.println("원판 입력 끝 -----------");

		for (int i = 0; i < T; i++) {
			st = new StringTokenizer(br.readLine());
			x = Integer.parseInt(st.nextToken());
			d = Integer.parseInt(st.nextToken());
			k = Integer.parseInt(st.nextToken());
			// System.out.println("-----명령어 " + x + "의 배수 원판을 " + d + "방향으로 " + k + "번
			// 회전------------------------");
			rotate();
		}

		// System.out.println("최종 원판 회전 결과 출력 --------");
		// for (int i = 0; i < N; i++) {
		// System.out.println(Arrays.toString(arr[i]));
		// }

		System.out.println(sum);
	}

	static void rotate() {
		// 먼저 회전 시키기
		for (int i = x; i <= N; i += x) { // 실제 배열은 0,0 부터 시작함 고로 밑에 i-1 처리해줌
			// System.out.println(i + "번째 원판 회전");
			int[] tmp = new int[M]; // 임시 배열
			if (d == 0) {// 시계방향
				for (int j = 0; j < M; j++) {
					// tmp[0]=arr[0-k]
					tmp[j] = arr[i - 1][(j - k + M) % M];
				}
			} else { // 반시계방향
				for (int j = 0; j < M; j++) {
					// tmp[0]=arr[0+k]
					tmp[j] = arr[i - 1][(j + k + M) % M];
				}
			}
			arr[i - 1] = tmp;
		}

		flag = false;// 인접한 수가 있는 지 체크용 플래그
		// 탐색 돌려서 인접한 수 찾기
		boolean[][] visit = new boolean[N][M];
		boolean[][] find = new boolean[N][M];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				// 시작점
				if (visit[i][j] || arr[i][j] == 0 || find[i][j]) { // 방문했거나 숫자가 0이면 넘기기
					continue;
				}
				bfs(i, j, visit, find);
			}
		}
		// System.out.println("bfs 탐색 끝");
		// for (int i = 0; i < N; i++) {
		// System.out.println(Arrays.toString(arr[i]));
		// }
		if (flag) {
			// System.out.println("인접한 같은 수가 있는 경우");
			for (int i = 0; i < N; i++) {
				// System.out.println(Arrays.toString(find[i]));
				for (int j = 0; j < M; j++) {
					if (find[i][j]) {
						sum -= arr[i][j];
						arr[i][j] = 0;
						cnt--;
					}
				}
			}
		} else {
			// 다 돌았는데 false면 평균 기준으로 + - 하기 평균은 소수점까지 고려하기
			double avg = (double) sum / cnt;
			// System.out.println("평균 " + avg);
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < M; j++) {
					if (arr[i][j] == 0) {
						continue;
					} else if (arr[i][j] > avg) {
						arr[i][j] -= 1;
						sum -= 1;
					} else if (arr[i][j] < avg) {
						arr[i][j] += 1;
						sum += 1;
					}
				}
			}
		}
		// System.out.println("명령어 끝나기 전에 상황 " + sum + " " + cnt);
	}

	static int[] dx = { 0, 0, 1, -1 }; // 오른쪽 왼쪽 아래
	static int[] dy = { 1, -1, 0, 0 };
	// 자기보다 안쪽(작은) 원판은 신경 쓸 필요가 없다고 생각함

	static void bfs(int x, int y, boolean[][] visit, boolean[][] find) {
		// 탐색해서 인접한 수 찾기
		// System.out.println("bfs 시작 " + x + " " + y + " " + arr[x][y]);
		visit[x][y] = true;
		Queue<int[]> q = new LinkedList<>(); // 행, 열, 숫자 저장하기
		q.add(new int[] { x, y, arr[x][y] });

		while (!q.isEmpty()) {
			int[] tmp = q.poll();
			for (int i = 0; i < 4; i++) {
				int cx = tmp[0] + dx[i]; // 원판
				int cy = (tmp[1] + dy[i] + M) % M; // 원판 내부

				if (cx >= N || cx < 0 || visit[cx][cy] || tmp[2] != arr[cx][cy]) {
					// 범위를 넘어갔거나 방문했거나 숫자가 다르면 넘어가기
					continue;
				}

				flag = true;
				visit[cx][cy] = true;
				find[x][y] = true;
				// find[tmp[0]][tmp[1]] = true;
				find[cx][cy] = true;
				q.add(new int[] { cx, cy, arr[cx][cy] });

			}
		}
	}
}

// 틀린 이유 1
// 평균보다 크면 -1
// 평균보다 작으면 +1 인데
// >= 경우 -1로 처리했다
// 평균인 경우에는 건들면 안됨

// 틀린 이유 2
// 인접한 수가 없을 때 평균보다 큰거 작은거를 sum에 처리 안해줌

// 틀린 이유 3
// 2의 배수 처리시에
// 1 2 3 4 하고 i%2==0 일때 continue는 코드가 더러워서
// i=0;i<N;i+=n 으로 했는데
// i=2 부터 시작이 아닌
// i=0 으로 시작하는 것으로 처리를 해서 틀림

// 생각1 -> 상관 없다 원상복구함
// find[x][y] = true; 를
// find[tmp[0]][tmp[1]] = true; 로 고침

// 틀린 이유 4 -> 마지막 실수...
// dx,dy에서 윗쪽 -1,0 경우 추가함....

// 2:47 소요.... 후.....