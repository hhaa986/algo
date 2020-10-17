//201017 새로운게임2
package baekjoon;

import java.io.*;
import java.util.*;

public class Main17837 {
				//우 좌 상 하 
	static int[] dirX = {0,0,-1,1} , dirY = {1,-1,0,0};
	static int N, K, color[][];
	static Horse[] horse;
	static ArrayList<Integer>[][] map;
	public static void main(String[] args) throws Exception{
		System.setIn(new FileInputStream("res/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		//input 
		N = Integer.parseInt(st.nextToken());	//map크기
		K = Integer.parseInt(st.nextToken());	//말의 개수
		horse = new Horse[K+1];
		map = new ArrayList[N][N];
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				map[i][j] = new ArrayList<Integer>();
			}
		}
		color = new int[N][N];	//0흰 1빨 2파
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				color[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		for(int i=0; i<K; i++) {
			horse[i] = new Horse();
			st = new StringTokenizer(br.readLine());
			horse[i].x = Integer.parseInt(st.nextToken()) - 1;
			horse[i].y = Integer.parseInt(st.nextToken()) - 1;
			horse[i].d = Integer.parseInt(st.nextToken()) - 1;
			horse[i].idx = i;
			map[horse[i].x][horse[i].y].add(i);
		}
//		mapPrint();
		
		//solve
		int game = 0;
		loop:while(true) {
			if(game > 1000) {
				game = -1;
				break;
			}
			game++;
			//color(맵의색) , map(말들 위치), horse(말 배열)
			//순서대로 말 이동
			for(int i=0; i<K; i++) {
				int x = horse[i].x;
				int y = horse[i].y;
				int d = horse[i].d;
				int xx = x + dirX[d];
				int yy = y + dirY[d];
				if(xx>=0 && yy>=0 && xx<N && yy<N) {
					//흰색
					List<Integer> list = null;
					if(color[xx][yy] == 0) {
						list = new ArrayList<>();
						//1. map에서 i의 인덱스 찾기
						int idx = searchIdx(x, y, i);
						//2. List에서 i부터 끝까지 담기 -> 끝부터 i까지 remove
						list = getList(idx, x, y);
						//3. xx,yy에 차례대로 담기
						for(int l=0; l<list.size(); l++) {
							map[xx][yy].add(list.get(l));
						}
						//숫자 바꾸기
						for(int l=0; l<list.size(); l++) {
							horse[list.get(l)].x = xx;
							horse[list.get(l)].y = yy;
						}
//						mapPrint();
						if(fourSearch()) {
							break loop;
						}
					}
					//빨강
					else if(color[xx][yy] == 1) {
						list = new ArrayList<>();
						//1. map에서 i의 인덱스 찾기
						int idx = searchIdx(x, y, i);
						//2. List에서 i부터 끝까지 담기 -> 끝부터 i까지 remove
						list = getList(idx, x, y);
						//3. xx,yy에 거꾸로 담기
						for(int l=list.size() -1; l>=0; l--) {
							map[xx][yy].add(list.get(l));
						}
						//숫자 바꾸기
						for(int l=0; l<list.size(); l++) {
							horse[list.get(l)].x = xx;
							horse[list.get(l)].y = yy;
						}
						if(fourSearch()) {
							break loop;
						}
					}
					//파란
					else if(color[xx][yy] == 2) {
						//방향 바꾸고 다시 xx, yy 정하기.
						horse[i].d = changeDir(d);
						d = horse[i].d;
						xx = x + dirX[d];
						yy = y + dirY[d];
						if(xx>=0 && yy>=0 && xx<N && yy<N) {
							find(xx, yy, x, y, i);
						}
						if(fourSearch()) {
							break loop;
						}
					}
//					mapPrint();
				}
				else {
					//빈칸
					horse[i].d = changeDir(d);
					d = horse[i].d;
					xx = x + dirX[d];
					yy = y + dirY[d];
					if(xx>=0 && yy>=0 && xx<N && yy<N) {
						find(xx, yy, x, y, i);
					}
					if(fourSearch()) {
						break loop;
					}
				}
			}
//			mapPrint();
//			printHorse();
		}
		//output
		System.out.println(game);
	}
	
	private static void horseSort() {
		for(int i=0; i<K; i++) {
			for(int j=i+1; j<K; j++) {
				if(horse[i].idx > horse[j].idx) {
					Horse temp = horse[i];
					horse[i] = horse[j];
					horse[j] = temp;
				}
			}
		}
	}

	

	private static void find(int xx, int yy, int x, int y, int i) {
		//흰색
		List<Integer>list = null;
		if(color[xx][yy] == 0) {
			list = new ArrayList<>();
			//1. map에서 i의 인덱스 찾기
			int idx = searchIdx(x, y, i);
			//2. List에서 i부터 끝까지 담기 -> 끝부터 i까지 remove
			list = getList(idx, x, y);
			//3. xx,yy에 차례대로 담기
			for(int l=0; l<list.size(); l++) {
				map[xx][yy].add(list.get(l));
			}
			//숫자 바꾸기
			for(int l=0; l<list.size(); l++) {
				horse[list.get(l)].x = xx;
				horse[list.get(l)].y = yy;
			}
//			mapPrint();
		}
		//빨강
		else if(color[xx][yy] == 1) {
			list = new ArrayList<>();
			//1. map에서 i의 인덱스 찾기
			int idx = searchIdx(x, y, i);
			//2. List에서 i부터 끝까지 담기 -> 끝부터 i까지 remove
			list = getList(idx, x, y);
			//3. xx,yy에 거꾸로 담기
			for(int l=list.size() -1; l>=0; l--) {
				map[xx][yy].add(list.get(l));
			}
			for(int l=0; l<list.size(); l++) {
				horse[list.get(l)].x = xx;
				horse[list.get(l)].y = yy;
			}
//			mapPrint();
		}
		
	}

	private static List<Integer> getList(int idx, int x, int y) {
		List<Integer> res = new ArrayList<>();
		for(int i = idx; i < map[x][y].size(); i++) {
			res.add(map[x][y].get(i));
		}
		for(int i=map[x][y].size() -1; i>=idx; i--) {
			map[x][y].remove(i);
		}
		return res;
	}

	private static int searchIdx(int x, int y, int idx) {
		int res = 0;
		for(int i=0; i<map[x][y].size(); i++) {
			if(map[x][y].get(i) == idx) {
				res = i;
				break;
			}
		}
		return res;
	}
	private static int changeDir(int d) {
		if(d == 0) return 1;
		else if(d == 1) return 0;
		else if (d == 2) return 3;
		else return 2;
	}

	private static void printHorse() {
		System.out.println("printHorse()");
		for(int i=0; i<K; i++) {
			System.out.println("x:"+ horse[i].x+" y:"+horse[i].y+" d:"+horse[i].d+" idx:"+horse[i].idx);
		}
		
	}

	private static boolean fourSearch() {
		boolean flag = false;
		loop:for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				int size = map[i][j].size();
				if(size >= 4) {
					flag = true;
					break loop;
				}
			}
		}
		return flag;
	}
	static class Horse{
		int x;
		int y;
		int d;
		int idx;
		Horse(){
			x = 0;
			y = 0;
			d = 0;
			idx = 0;
		}
		Horse(int x, int y, int d, int idx){
			this.x = x;
			this.y = y;
			this.d = d;
			this.idx = idx;
		}
	}

	private static void mapPrint() {
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				int size = map[i][j].size();
				System.out.print("{");
				for(int k=0; k<size; k++) {
					System.out.print(" "+map[i][j].get(k));
				}System.out.print(" }");
			}System.out.println();
		}
		
	}
}
