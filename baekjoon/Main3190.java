package baekjoon;

import java.io.*;
import java.util.*;

public class Main3190 {
	static int N, K, L, X, cnt, headX, headY, tailX, tailY;
	static int[] dirX = { 0, 1, 0, -1 }; // 우 하 좌 상
	static int[] dirY = { 1, 0, -1, 0 }; // 우 하 좌 상
	static int map[][], snakeTime[];
	static char[] snakeDir;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 입력
		N = Integer.parseInt(st.nextToken()); // 맵 크기
		st = new StringTokenizer(br.readLine());
		K = Integer.parseInt(st.nextToken()); // 사과 개수
		map = new int[N][N];
		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			map[a - 1][b - 1] = -1; // 사과 행 좌표
		}
		L = Integer.parseInt(br.readLine().trim()); // 뱀 방향 횟수
		snakeTime = new int[L];
		snakeDir = new char[L];
		for (int i = 0; i < L; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			String b = st.nextToken();
			snakeTime[i] = a; // 뱀 방향전환 시간
			snakeDir[i] = b.charAt(0); // 뱀 방향 어디?
		}
		headX = 0;
		headY = 0;
		tailX = 0;
		tailY = 0;

		// 풀이
		solve(headX, headY, 1);

		// 출력
		System.out.println(cnt);
		br.close();
	}

	private static void solve(int x, int y, int dirSnake) { // 시뮬 시작!
		map[x][y] = dirSnake;
		int newX = x + dirX[dirSnake - 1];
		int newY = y + dirY[dirSnake - 1];
		if (newX < 0 || newY < 0 || newX >= N || newY >= N || (map[newX][newY] != 0 && map[newX][newY] != -1)) {
			cnt++;
			return;
		}
		if (map[newX][newY] == -1) { // 사과 먹음
			map[newX][newY] = dirSnake;
			cnt++;
			for (int t = 0; t < L; t++) { // 방향돌리는 시간 확인
				if (cnt == snakeTime[t]) { // 시간찾음!!
					if (snakeDir[t] == 'D') {
						if (dirSnake != 4)
							dirSnake++;
						else
							dirSnake = 1;
						solve(newX, newY, dirSnake);
						return;
					} else {
						if (dirSnake != 1)
							dirSnake--;
						else
							dirSnake = 4;
						solve(newX, newY, dirSnake);
						return;
					}
				}
			}
			// 방향 안돌림
			solve(newX, newY, dirSnake);
		} else { // 사과 없음ㅠ
			map[newX][newY] = dirSnake;
			int dirTail = map[tailX][tailY];
			// System.out.println("dirTail:"+dirTail);
			map[tailX][tailY] = 0;
			tailX = tailX + dirX[dirTail - 1];
			tailY = tailY + dirY[dirTail - 1];
			cnt++;
			for (int t = 0; t < L; t++) { // 방향돌리는 시간 확인
				if (cnt == snakeTime[t]) { // 시간찾음!!
					if (snakeDir[t] == 'D') {
						if (dirSnake != 4)
							dirSnake++;
						else
							dirSnake = 1;
						solve(newX, newY, dirSnake);
						return;
					} else {
						if (dirSnake != 1)
							dirSnake--;
						else
							dirSnake = 4;
						solve(newX, newY, dirSnake);
						return;
					}
				}
			}
			// 방향 안돌림
			solve(newX, newY, dirSnake);
		}

	}
}
