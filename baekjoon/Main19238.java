package baekjoon;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main19238 {
	static int dirX[] = {-1,0,1,0}, dirY[] = {0,-1,0,1};
	static int N, M, oil, driverX, driverY, map[][];
	static Person p[];
	static boolean error = false;
	public static void main(String[] args) throws Exception{
		System.setIn(new FileInputStream("res/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		//input 
		N = Integer.parseInt(st.nextToken());	//map크기
		M = Integer.parseInt(st.nextToken());	//사람 수
		oil = Integer.parseInt(st.nextToken());	//연료
//		System.out.println(oil);
		map = new int[N][N];
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == 1) map[i][j] = -1;
			}
		}
		st = new StringTokenizer(br.readLine());
		driverX = Integer.parseInt(st.nextToken()) -1;
		driverY = Integer.parseInt(st.nextToken()) -1;
		p = new Person[M];
		for(int i=1; i<=M; i++) {
			st = new StringTokenizer(br.readLine());
			int sx = Integer.parseInt(st.nextToken()) -1;
			int sy = Integer.parseInt(st.nextToken()) -1;
			int ax = Integer.parseInt(st.nextToken()) -1;
			int ay = Integer.parseInt(st.nextToken()) -1;
			p[i - 1] = new Person(sx, sy, ax, ay);
			map[sx][sy] = i;
		}
		
//		for(int[] a :map) System.out.println(Arrays.toString(a));
//		for(Person a :p) System.out.println(a.toString());
//		System.out.println("driver: "+driverX+" "+driverY);
		
		//solve + output
		for(int i=0; i<M; i++) {
			driveBfs(driverX, driverY);
		}
		loop:for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(map[i][j] > 0 && !error) {
					error = true;
					break loop;
				}
			}
		}
		if(error) {
			System.out.println(-1);
		}
		else {
			System.out.println(oil);
		}
	}
	
	private static void driveBfs(int dx, int dy) {
		boolean[][] visited = new boolean[N][N];
		Queue<int[]> q = new LinkedList<>();
		
		int useOil = 0;
		q.offer(new int[] {dx, dy});
		if (map[dx][dy] > 0) {
			p[map[dx][dy] - 1].sx = -1;
			p[map[dx][dy] - 1].sy = -1;
			int ax = p[map[dx][dy] -1].ax;
			int ay = p[map[dx][dy] -1].ay;
			map[dx][dy] = 0;
			while(!q.isEmpty()) q.poll();
			q.offer(new int[] {ax, ay});
			if(!isArrived(dx, dy, ax, ay)) {
				error = true;
				return;
			}
		}
//		System.out.println("dx: "+dx+" || dy: "+dy);
		
		while(!q.isEmpty()) {
			useOil++;
			int qsize = q.size();
			for(int w=0; w<qsize; w++) {
				int[] curr = q.poll();
				visited[curr[0]][curr[1]] = true;
				for(int d=0; d<4; d++) {
					int dxx = curr[0] + dirX[d];
					int dyy = curr[1] + dirY[d];
					if(dxx>=0 && dxx<N && dyy>=0 && dyy<N && map[dxx][dyy] != -1 && !visited[dxx][dyy]) {
	//					System.out.println("dxx: "+dxx+" || dyy: "+dyy+ " ||oil: "+oil+ " ||useoil: "+useOil);
						if(map[dxx][dyy] > 0) {
							useOil++;
							visited = new boolean[N][N];
//							System.out.println(useOil+"oil: "+oil);
							oil -= useOil;
							useOil = 0;
							driverX = dxx;
							driverY = dyy;
							p[map[driverX][driverY] -1].sx = -1;
							p[map[driverX][driverY] -1].sy = -1;
							int ax = p[map[driverX][driverY] - 1].ax;
							int ay = p[map[driverX][driverY] - 1].ay;
							map[driverX][driverY] = 0;
							visited[driverX][driverY] = true;
							
							while(!q.isEmpty()) q.poll();
						
							q.offer(new int[] {ax, ay});
//							System.out.println(ax+" "+ay);
							if(!isArrived(driverX, driverY, ax, ay)) {
								error = true;
								return;
							}
						}
						else {
							visited[dxx][dyy]  = true;
							q.offer(new int[] {dxx, dyy});
						}
					}
				}
			}
			if(oil - useOil == 0) {
				error = true;
				return;
			}
//			for(boolean[] a :visited) System.out.println(Arrays.toString(a));
//			for(int[] a :map) System.out.println("?"+Arrays.toString(a));
//			System.out.println();
			
		}
	}
	
	private static boolean isArrived(int dx, int dy, int ax, int ay) {
//		System.out.println("isArrived start");
//		System.out.println(ax+" "+ay);
		boolean[][] checked = new boolean[N][N];
		Queue<int[]> q = new LinkedList<>();
		q.offer(new int[] {dx, dy});
		checked[dx][dy] = true;
//		for(boolean[] a :checked) System.out.println(Arrays.toString(a));
//		System.out.println("first\n");
		int useOil = 0;
		while(!q.isEmpty()) {
			
//			System.out.println(useOil+" "+oil);
			int qsize = q.size();
			for(int i=0; i<qsize; i++) {
				int[] curr = q.poll();
				
				for(int d=0; d<4; d++) {
					int dxx = curr[0] + dirX[d];
					int dyy = curr[1] + dirY[d];
					if(dxx>=0 && dxx<N && dyy>=0 && dyy<N && map[dxx][dyy] != -1 && !checked[dxx][dyy]) {
						if(dxx == ax && dyy == ay) {
//							System.out.println(useOil+" oil:"+oil);
							oil += useOil;
							driverX = dxx;
							driverY = dyy;
							return true;
						}
						checked[dxx][dyy] = true;
						q.offer(new int[] {dxx, dyy});
					}
				}
			}
//			for(boolean[] a :checked) System.out.println(Arrays.toString(a));
//			System.out.println();
			useOil++;
			if(oil - useOil == 0) return false;
		}
		return false;
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
