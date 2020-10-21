//201017 청소년상어
package baekjoon;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main19236 {
	//상 상좌 좌 좌하  하 하우 우 우상
	static int dirX[] = {-1,-1,0,1,1,1,0,-1}, dirY[] = {0,-1,-1,-1,0,1,1,1}; 
	static int  max;
	public static void main(String[] args) throws Exception{
		System.setIn(new FileInputStream("res/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;
        //input
        int [][] map = new int[4][4];
        Fish[] fishes = new Fish[16];
        for(int i=0; i<16; i++) {
        	fishes[i] = new Fish();
        }
        
        for(int i=0; i<4; i++) {
        	st = new StringTokenizer(br.readLine());
        	for(int j=0; j<4; j++) {
        		int num = Integer.parseInt(st.nextToken()) - 1;
        		fishes[num].x = i;
        		fishes[num].y = j;
        		fishes[num].d = Integer.parseInt(st.nextToken())-1;
        		map[i][j] = num;
        	}
        }
//        for(int i=0; i<16; i++) {
//        	System.out.println("{"+i+" : " +fishes[i].x+" "+fishes[i].y+" "+ fishes[i].d+" }");
//        }
        for(int[] a : map) System.out.println(Arrays.toString(a));
        System.out.println();
        boolean[] check = new boolean[16];

        //solve
        int eat = map[0][0] + 1;
        check[map[0][0]] = true;
        int sharkX = 0;
        int sharkY = 0;
        int sharkD = fishes[map[0][0]].d;
        map[0][0] = -1;
        
        //fish Move
        max = Integer.MIN_VALUE;
        solve(sharkX, sharkY, sharkD, eat, map, fishes, check);
        
        //output
        System.out.println(max);
		
		
	}
	private static void solve(int x, int y, int d, int eat, int[][] map, Fish[] fishes, boolean[] check) {
		
	
		fishMove(fishes, map, x, y, check);
		for(int i=1; i<4; i++) {
			int xx = x + dirX[d] *i;
			int yy = y + dirY[d] *i;
			if(xx >=0 && yy>=0 && xx<4 && yy<4 && map[xx][yy] > -1) {
				int[][] copyMap = copyMap(map);
				Fish[] copyFish = copyFish(fishes);
				
				copyMap[x][y] = -1;
				int newEat = eat + (copyMap[xx][yy] + 1);
				int fish = copyMap[xx][yy];
				int sharkD = copyFish[fish].d;
				copyMap[xx][yy] = -1;
				boolean[] copyCheck = new boolean[16];
				for(int z=0; z<16; z++) {copyCheck[z] = check[z];}
				copyCheck[fish] = true;
				solve(xx, yy, sharkD, newEat, copyMap, copyFish, copyCheck);
			}
		}
		max = Math.max(max, eat);	
	}
	private static Fish[] copyFish(Fish[] fishes) {
		Fish[] temp = new Fish[16];
		for(int i=0; i<temp.length; i++) {
			temp[i] = fishes[i];
		}
        return temp;
	}
	private static int[][] copyMap(int[][] map) {
		 int[][] temp = new int[4][4];

	        for (int i = 0; i < 4; i++) {
	            for (int j = 0; j < 4; j++) {
	                temp[i][j] = map[i][j];
	            }
	        }

	        return temp;
	}
	private static void fishMove(Fish[] fishes, int[][] map, int sharkX, int sharkY, boolean[] check) {
		loop :for(int i=0; i<16; i++) {
        	if(check[i]) continue;	//죽은물고기는 움직일 수 없다
        	int x = fishes[i].x;
        	int y = fishes[i].y;
        	int d = fishes[i].d;
        	
        	for(int j=0; j<8; j++) {
        		d = (d+j) % 8;
        		int xx = x + dirX[d];
        		int yy = y + dirY[d];
        		if(xx>=0 && yy>=0 && xx<4 && yy<4 && !(xx == sharkX && yy == sharkY)) {
        			if(map[xx][yy] == -1) {
        				//빈칸
        				fishes[i].x = xx;
        				fishes[i].y = yy;
        				fishes[i].d = d;
        				map[xx][yy] = map[x][y];
        				map[x][y] = -1;
        				continue loop;
        			}
        			else {
        				//물고기 있음
        				int tmpn = map[xx][yy];
        				fishes[tmpn].x = x;
        				fishes[tmpn].y = y;
        				map[x][y] = tmpn;
        				fishes[i].x = xx;
        				fishes[i].y = yy;
        				fishes[i].d = d;
        				map[xx][yy] = i;
        				continue loop;
        			}
        		}
        	}
        }
		System.out.println("물고기이동..");
        for(int[] a : map) System.out.println(Arrays.toString(a));
        System.out.println();
		
	}
	static class Fish{
		int x;
		int y;
		int d;
		Fish(){
			x = 0;
			y = 0;
			d = 0;
		}
		Fish(int x, int y, int d){
			this.x = x;
			this.y = y;
			this.d = d;
		}
		
	}
}
