//201012
package baekjoon;

import java.io.*;
import java.util.*;


public class Main17143 {
	
	public static void main(String[] args) throws Exception{
		System.setIn(new FileInputStream("res/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		//input
		int dirX[] = {-1, 1, 0, 0};
		int dirY[] = {0, 0, 1, -1};	//상 하 우 좌
		int R = Integer.parseInt(st.nextToken());//행
		int C = Integer.parseInt(st.nextToken());//열
		int M = Integer.parseInt(st.nextToken());//상어
		List<Shark> sh = new ArrayList<>();
		int[][] sizeMap = new int[R][C];
		for(int i=0; i<M; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken()) - 1;	//행
			int y = Integer.parseInt(st.nextToken()) - 1;	//열
			int v = Integer.parseInt(st.nextToken());		//속도
			int d = Integer.parseInt(st.nextToken()) - 1;	//방향
			int s = Integer.parseInt(st.nextToken());		//크기
			
			sh.add(new Shark(x, y, d, v, s));
			sizeMap[x][y] = s;
		}
//		for (int[] is : sizeMap) System.out.println(Arrays.toString(is));
//		System.out.println();
//		for(int i=0; i<sh.size(); i++){
//			System.out.println(sh.get(i).toString());
//		}System.out.println();
		//solve
		int cnt = 0;
		Collections.sort(sh, comp); // y정렬 -> x정렬
		for(int human=0; human<=C; human++) { 
			Collections.sort(sh, comp);
			if(human == C) break;
			//1. 상어 잡기(같은 행의 상어+가장 가까운 행 / 없으면 안잡는다)
			for (int j=0; j<sh.size(); j++) {
				Shark ssh = sh.get(j);
				if(ssh.y == human) {
					cnt += sizeMap[ssh.x][ssh.y];
					sizeMap[ssh.x][ssh.y] = 0;
					sh.remove(j);
					break;
				}
			}
			//2. 상어이동(상하 or 좌우)
			int[][] tmpMap = new int[R][C];
			List<Integer> removeS = new ArrayList<>();
			for(int j=0; j<sh.size(); j++) {
				Shark ssh = sh.get(j);
//				System.out.println("처음x,y: "+ssh.x+" "+ssh.y);
				if(ssh.d == 0 || ssh.d == 1) {//상하
					int size = sizeMap[ssh.x][ssh.y];
					for(int k=0; k<ssh.v%((R-1)*2); k++) {//속도만큼 돈다
//						System.out.println(k+" x,y : "+ssh.x+" "+ssh.y);
						int xx = ssh.x + dirX[ssh.d];
						if(isMap(xx, R)) {//map안에 있을때
							ssh.x = xx;
						}
						else {//map안에 없을때
							ssh.d = Math.abs(ssh.d - 1);
							xx = ssh.x + dirX[ssh.d];
							ssh.x = xx;
						}
					}//속도만큼 이동 완료
					//멈춘 자리 확인
					if(tmpMap[ssh.x][ssh.y] == 0){
						tmpMap[ssh.x][ssh.y] = size;
					}
					else if(tmpMap[ssh.x][ssh.y] < size) {
						removeS.add(tmpMap[ssh.x][ssh.y]);
						tmpMap[ssh.x][ssh.y] = size;
					}
					else {
						removeS.add(size);
					}
				}
				else if(ssh.d == 2 || ssh.d == 3) {//우좌
					int size = sizeMap[ssh.x][ssh.y];
					for(int k=0; k<ssh.v%((C-1)*2); k++) {//속도만큼 돈다
//						System.out.println(k+" x,y : "+ssh.x+" "+ssh.y);
						int yy = ssh.y + dirY[ssh.d];
						if(isMap(yy, C)) {//map안에 있을때
							ssh.y = yy;
						}
						else {//map안에 없을때
							if(ssh.d == 2) {
								ssh.d = 3;
							}
							else {
								ssh.d = 2;
							}
							yy = ssh.y + dirY[ssh.d];
							ssh.y = yy;
						}
					}//속도만큼 이동 완료
					//멈춘 자리 확인
//					System.out.println(ssh.x+" "+ssh.y);
					if(tmpMap[ssh.x][ssh.y] == 0){
						tmpMap[ssh.x][ssh.y] = size;
					}
					else if(tmpMap[ssh.x][ssh.y] < size) {
						removeS.add(tmpMap[ssh.x][ssh.y]);
						tmpMap[ssh.x][ssh.y] = size;
					}
					else {
						removeS.add(size);
					}
				}
			}
			for(int sz :removeS) {
				for(int i=0; i<sh.size(); i++) {
					if(sh.get(i).s == sz) {
						sh.remove(i);
						break;
					}
				}
			}
			for(int i=0; i<R; i++) {
				for(int j=0; j<C; j++) {
					sizeMap[i][j] = tmpMap[i][j];
				}
			}
			
//			for (int[] is : sizeMap) System.out.println(Arrays.toString(is));
//			System.out.println(cnt+"\n");
		}//human end
		
		
		//output
		System.out.println(cnt);
		

	}
	private static boolean isMap(int x, int R) {
		if (x >=0 && x <R) {
			return true;
		}
		return false;
	}
	static Comparator<Shark> comp =  (o1,o2) ->{
		if(o1.y == o2.y) {
			return o1.x - o2.x;
		}
		else 
			return o1.y - o2.y;
	};
	static class Shark{
		int x;
		int y;
		int d;
		int v;
		int s;
		
		Shark(int x, int y, int d, int v, int s){
			this.x = x;
			this.y = y;
			this.d = d;
			this.v = v;
			this.s = s;
		}
		Shark(){}
		public String toString() {
			String tmp = "x: "+x+", y: "+y+", d: "+d+", v: "+v+", s: "+s;
			return tmp;
		}
	}

}
