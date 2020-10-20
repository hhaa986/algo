//201019 벽 부수고 이동하기
package baekjoon;

import java.io.*;
import java.util.*;

public class Main2206 {
	static int N, M, min, map[][];
	static int[] dirX = {-1,1,0,0}, dirY = {0,0,-1,1};
	static boolean[][] check = new boolean[N][M];
	static boolean end = false;

	public static void main(String[] args) throws Exception{
		System.setIn(new FileInputStream("res/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		//input
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		int wallCnt = 0;
		List<int[]> wall = new ArrayList<>();
		for(int i=0; i<N; i++){
			String s = br.readLine();
			for(int j=0; j<M; j++){
				map[i][j] = s.charAt(j) - '0';
				if(map[i][j] == 1) {
					wallCnt++;
					wall.add(new int[]{i,j});
				}
			}
		}
//		for(int[] a:map) System.out.println(Arrays.toString(a));

		//solve
		min = Integer.MAX_VALUE;
		for(int i=0; i<wallCnt; i++){
			int[][] temp = copyMap();
			int wx = wall.get(i)[0];
			int wy = wall.get(i)[1];
			temp[wx][wy] = 0;
			check = new boolean[N][M];
			check[0][0] = true;
			bfs(0,0, temp);
//			System.out.println();
//			for(int[] a:temp) System.out.println(Arrays.toString(a));
		}

		//output
		if(end) System.out.println(min);
		else System.out.println(-1);
	}

	private static void bfs(int x, int y, int[][] tmpMap) {
		Queue<int[]> q = new LinkedList<>();
		q.offer(new int[] {x, y});
		int cnt = 1;
		loop:while(!q.isEmpty()){
			cnt++;
			int size = q.size();
			for(int s=0; s<size; s++) {
				int[] curr = q.poll();
				for (int d = 0; d < 4; d++) {
					int xx = curr[0] + dirX[d];
					int yy = curr[1] + dirY[d];
					if (xx >= 0 && yy >= 0 && xx < N && yy < M && !check[xx][yy] && tmpMap[xx][yy] == 0) {
						if (xx == N - 1 && yy == M - 1) {
							min = Math.min(min, cnt);
							end = true;
							break loop;
						}
						check[xx][yy] = true;
						q.offer(new int[]{xx,yy});
					}
				}
			}

			if(cnt > min) break;
		}
	}

	private static int[][] copyMap() {
		int[][] temp = new int[N][M];
		for(int i=0; i<N; i++){
			for(int j=0; j<M; j++) {
				temp[i][j] = map[i][j];
			}
		}
		return temp;
	}
}