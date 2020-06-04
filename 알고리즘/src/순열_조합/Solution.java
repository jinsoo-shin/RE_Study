package 순열_조합;

//순열 permutation
//조합 combination
import java.util.*;

public class Solution {
	static int N = 3;
	static int R = 2;

	public static void main(String[] args) {
		System.out.println("---------비트마스크 조합---------");
		combination_bitmask();
		System.out.println("---------재귀 조합---------");
		combination_recursive(new boolean[N], 0);
		System.out.println("---------재귀 조합 nCr-----");
		nCr(new boolean[N], 0, 0);
		System.out.println("----------재귀 순열-------");
		int[] arr = new int[N];
		for (int i = 0; i < N; i++) {
			arr[i] = i;
		}
		permutation(arr, 0);
		System.out.println("중복 순열");
		List<Integer> list = new LinkedList<>();
		duplicate_permutation(list, 0);
	}

	static void swap(int[] arr, int a, int b) {
		int tmp = arr[a];
		arr[a] = arr[b];
		arr[b] = tmp;
	}

	static void combination_recursive(boolean[] flag, int idx) {
		if (idx == N) {
			print(flag);
			return;
		}

		flag[idx] = true;
		combination_recursive(flag, idx + 1);
		flag[idx] = false;
		combination_recursive(flag, idx + 1);
	}

	static void combination_bitmask() {

		boolean[] arr = new boolean[N];
		for (int i = 0; i < (1 << N); i++) {
			arr = new boolean[N];
			for (int j = 0; j < N; j++) {
				if (((1 << j) & i) != 0) {
					arr[j] = true;
				}
			}
			// System.out.println(Arrays.toString(arr));
			print(arr);
		}
	}

	static void nCr(boolean[] flag, int idx, int cnt) {
		if (cnt > N) {
			return;
		}
		if (idx == N) {
			if (cnt == R) {
				print(flag);
			}
			return;
		}

		flag[idx] = true;
		nCr(flag, idx + 1, cnt + 1);
		flag[idx] = false;
		nCr(flag, idx + 1, cnt);
	}

	static void permutation(int[] arr, int idx) {
		if (idx == N) {
			print(arr);
			return;
		}

		for (int i = idx; i < N; i++) {
			swap(arr, i, idx);
			permutation(arr, idx + 1);
			swap(arr, i, idx);
		}
	}

	static void duplicate_permutation(List<Integer> list, int idx) {
		if (idx == N) {
			System.out.println(list);
			return;
		}
		for (int i = 1; i <= N; i++) {
			list.add(i);
			duplicate_permutation(list, idx + 1);
			list.remove(idx);
		}
	}

	static void print(boolean[] arr) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; i++) {
			if (arr[i]) {
				sb.append(i).append(" ");
			} else {
				sb.append("- ");
			}
		}
		System.out.println(sb);
	}

	static void print(int[] arr) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < N; i++) {
			sb.append(arr[i]).append(" ");
		}
		System.out.println(sb);
	}

}
