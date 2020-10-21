//201014 201015 201017 스타트택시
package baekjoon;

import java.io.*;
import java.util.*;

public class Main19238 {
	static int dirX[] = {-1,0,1,0}, dirY[] = {0,-1,0,1};
	static int N, M, oil, map[][];
	static Person p[];
	static boolean check[][], pcheck[];
	static boolean error = false;
	public static void main(String[] args) throws Exception{
		System.setIn(new FileInputStream("res/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		//input 
		N = Integer.parseInt(st.nextToken());	//map크기
		M = Integer.parseInt(st.nextToken());	//사람 수
		oil = Integer.parseInt(st.nextToken());	//연료
		map = new int[N][N];
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == 1) map[i][j] = -1;//벽
			}
		}
		st = new StringTokenizer(br.readLine());
		int driverX = Integer.parseInt(st.nextToken()) -1;
		int driverY = Integer.parseInt(st.nextToken()) -1;
		p = new Person[M];//사람 인덱스 0~M-1
		for(int i=1; i<=M; i++) {
			st = new StringTokenizer(br.readLine());
			int sx = Integer.parseInt(st.nextToken()) -1;
			int sy = Integer.parseInt(st.nextToken()) -1;
			int ax = Integer.parseInt(st.nextToken()) -1;
			int ay = Integer.parseInt(st.nextToken()) -1;
			p[i - 1] = new Person(sx, sy, ax, ay);
			map[sx][sy] = i;
		}
		
		//solve

		check = new boolean[N][N];
		pcheck = new boolean[M];
		getPersonBfs(driverX, driverY);
		
		//output
	}

	private static void getPersonBfs(int dx, int dy) {
		Queue<int[]> q = new LinkedList<>();
		q.offer(new int[] {dx, dy});
		check[dx][dy] = true;
		int useOil = 0;
		while(!q.isEmpty()) {
			int qsize = q.size();
			
			List<int[]> temp = new ArrayList<>();
			for(int t=0; t<qsize; t++) {
				int[] curr = q.poll();
				for(int d=0; d<4; d++) {
					int dxx = curr[0]+dirX[d];
					int dyy = curr[1]+dirY[d];
					if(dxx>=0 && dyy>=0 && dxx<N && dyy<N && !check[dxx][dyy]) {
						if(map[dxx][dyy] > 0) {
							temp.add(new int[] {map[dxx][dyy], dxx, dyy});
						}
						check[dxx][dyy] = true;
						q.offer(new int[] {dxx, dyy});
					}
				}
			}
			oil--;//거리 플러스
			System.out.println("oil: "+oil);
			if(oil == 0) {
				error = true;
				return;
			}
			if(temp.size() > 0) {
				//손님 sort -> 행 -> 열
				Collections.sort(temp, new Comparator<int[]>() {
					@Override
					public int compare(int[] o1, int[] o2) {
						if(o1[1] == o2[1]) {
							return (o1[2]- o2[2]);
						}
						else 
							return (o1[1]- o2[1]);
					}});
				while(!q.isEmpty()) q.poll();
				check = new boolean[N][N];
				// 맨 첫번째 temp 이용해서 map 값 초기화
				int tx = temp.get(0)[1];
				int ty = temp.get(0)[2];
				int ti = temp.get(0)[0] -1;
				map[tx][ty] = 0;
				int tax = p[ti].ax;
				int tay = p[ti].ay;
				q.offer(new int[] {tx, ty});
				check[tx][ty] = true;
				pcheck[ti - 1] = true;// 목적지 도착한 다음에 ! 체크하기
				printMap();
				// 목적지 가는 bfs 고고
				//
				// 목적지만 q에 넣기
				//현재 위치(승객 만난곳) check = true;
				
			}
		}
	}

	private static void printMap() {
		System.out.println("printMap");
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				System.out.print(map[i][j]+" ");
			}System.out.println();
		}
	}

	static class Person {
		int sx;
		int sy;
		int ax;
		int ay;
		
		Person(int sx, int sy, int ax, int ay) {
			this.sx = sx;
			this.sy = sy;
			this.ax = ax;
			this.ay = ay;
		}
		Person(){}
		public String toString() {
			return "sx : "+sx+", sy : "+sy+", ax : "+ax+", ay : "+ay;
		}
	}
}
