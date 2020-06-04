package BOJ_17825_주사위윷놀이;

import java.util.*;
import java.io.*;

public class Main {

	static int ans;
	static int[] dice; // 입력
	static int[] score_map = { 0, 2, 4, 6, 8, 10, 13, 16, 19, 12, 14, 16, 18, 20, 22, 24, 22, 24, 26, 28, 30, 28, 27,
			26, 25, 32, 34, 36, 38, 30, 35, 40, 0 }; // 점수 맵
	static int[][] jump_map = { { 0, 1, 2, 3, 4, 5 }, // 0번
			{ 0, 2, 3, 4, 5, 9 }, // 1
			{ 0, 3, 4, 5, 9, 10 }, // 2
			{ 0, 4, 5, 9, 10, 11 }, // 3
			{ 0, 5, 9, 10, 11, 12 }, // 4
			{ 0, 6, 7, 8, 24, 29 }, // 5
			{ 0, 7, 8, 24, 29, 30 }, // 6
			{ 0, 8, 24, 29, 30, 31 }, // 7
			{ 0, 24, 29, 30, 31, 32 }, // 8
			{ 0, 10, 11, 12, 13, 16 }, // 9
			{ 0, 11, 12, 13, 16, 17 }, // 10
			{ 0, 12, 13, 16, 17, 18 }, // 11
			{ 0, 13, 16, 17, 18, 19 }, // 12
			{ 0, 14, 15, 24, 29, 30 }, // 13
			{ 0, 15, 24, 29, 30, 31 }, // 14
			{ 0, 24, 29, 30, 31, 32 }, // 15
			{ 0, 17, 18, 19, 20, 25 }, // 16
			{ 0, 18, 19, 20, 25, 26 }, // 17
			{ 0, 19, 20, 25, 26, 27 }, // 18
			{ 0, 20, 25, 26, 27, 28 }, // 19
			{ 0, 21, 22, 23, 24, 29 }, // 20
			{ 0, 22, 23, 24, 29, 30 }, // 21
			{ 0, 23, 24, 29, 30, 31 }, // 22
			{ 0, 24, 29, 30, 31, 32 }, // 23
			{ 0, 29, 30, 31, 32, 32 }, // 24
			{ 0, 26, 27, 28, 31, 32 }, // 25
			{ 0, 27, 28, 31, 32, 32 }, // 26
			{ 0, 28, 31, 32, 32, 32 }, // 27
			{ 0, 31, 32, 32, 32, 32 }, // 28
			{ 0, 30, 31, 32, 32, 32 }, // 29
			{ 0, 31, 32, 32, 32, 32 }, // 30
			{ 0, 32, 32, 32, 32, 32 }, // 31
			{ 0, 32, 32, 32, 32, 32 }, // 32 끝
	}; // 칸 이동 맵

	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("src/Baekjoon_17825_주사위윷놀이/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		ans = 0;

		dice = new int[10];
		for (int i = 0; i < 10; i++) {
			dice[i] = Integer.parseInt(st.nextToken());
		}

		comb(new int[10], 0, 1);
		System.out.println(ans);
	}

	static void comb(int[] arr, int idx, int num) {
		if (idx == arr.length) {
			// System.out.println("----------------------------------------------");
			// System.out.println(Arrays.toString(arr));
			// 여기서 calc로 넘기기
			calc(arr);
			return;
		}
		// System.out.println(num);
		for (int i = 1; i <= 4; i++) {
			arr[idx] = i;
			comb(arr, idx + 1, i + 1);
		}
	}

	static void calc(int[] arr) {

		int[] player = { 0, 0, 0, 0, 0 };// 위치 초기화
		boolean[] check = new boolean[33];

		int cur_player, cur_position, move, next_position;
		int score = 0;
		for (int idx = 0; idx < arr.length; idx++) {
			// 위치 파악하고
			cur_player = arr[idx];
			cur_position = player[cur_player];
			move = dice[idx];

			// 이동시키고
			next_position = jump_map[cur_position][move];

			// 이동을 마치는 칸이 도착 칸이면 고를 수 있다.
			// for (int p = 1; p <= 4; p++) {
			// // 말이 이동을 마치는 칸에 다른 말이 있으면 그 말은 고를 수 없다.
			// if (player[p] == next_position && player[p] != 32) {// 처음과 끝이 아니면서 겹치는 경우
			// return;
			// }
			// }

			if (check[next_position] && next_position != 32 && next_position != 0) { // 시작이랑 끝이아니면서 누군가가 있으면
				return;
			}
			// 갈수있으면 점수에 더해주고
			check[next_position] = true;
			check[cur_position] = false;
			player[cur_player] = next_position; // 위치 변경해주기
			score += score_map[next_position];// 점수 더하기
		}
		if (ans < score) {
			ans = score;
		}
	}
}

// 예제 4가 틀린 이유
// 사람을 3명으로 조합을 뽑았기때문에

// 11%에서 틀림 왜죠......

// 조합에서 틀려서 걍 틀린거였다