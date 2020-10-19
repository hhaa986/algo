//201019 벽 부수고 이동하기
package baekjoon;

import java.io.*;
import java.util.*;

public class Main2206 {
	public static void main(String[] args) throws Exception{
		System.setIn(new FileInputStream("res/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		//input
		int N = Integer.parseInt(st.nextToken());
		boolean[][] check = new boolean[N][N];
		int[][] map = new int[N][N];
		for(int i=0; i<N; i++){
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++){
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		for(int[] a:map) System.out.println(Arrays.toString(a));

		//solve


		//output



	}
}