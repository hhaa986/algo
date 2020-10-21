//201017 꽃길
package baekjoon;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main14620 {
	static int[] dirX = {0,-1,1,0,0},  dirY = {0,0,0,-1,1};
	static int N, min, map[][];
	static boolean[][] check;
	public static void main(String[] args) throws IOException {
		System.setIn(new FileInputStream("res/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		
		//input
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		//solve
		min = Integer.MAX_VALUE;
		check = new boolean[N][N];
		dfs(0, 0);
		
		
		//output
		System.out.println(min);
	}

	private static void dfs(int cnt, int val) {
		if(cnt == 3) {
//			for (boolean[] bs : check) {
//				System.out.println(Arrays.toString(bs));
//			}System.out.println();
			min = Math.min(min, val);
			return;
		}
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(!check[i][j]) {
					int checkCnt = 0;
					for(int d=1; d<=4; d++) {
						int xx = i + dirX[d];
						int yy = j + dirY[d];
						if(xx>=0 && yy>=0 && xx<N && yy<N && !check[xx][yy]) {
							checkCnt++;
						}
					}
					if(checkCnt == 4) {
						int value = 0;
						for(int d=0; d<=4; d++) {
							int xx = i + dirX[d];
							int yy = j + dirY[d];
							value += map[xx][yy];
							check[xx][yy] = true;
						}
						dfs(cnt + 1, val + value);
						for(int d=0; d<=4; d++) {
							int xx = i + dirX[d];
							int yy = j + dirY[d];
							check[xx][yy] = false;
						}
					}
				}
			}
		}
	}
}
