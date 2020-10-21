//201013 윷놀이 ㅡㅡ
package baekjoon;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main17825 {
	/**
	 * 1. 말 조합(10번)
	 * 2. 조합 결과 가지고 주사위 돌리기
	 * 
	 * */
	static int[] map = {
		0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 32,
		5, 21, 22, 23, 24, 25, 26, 20, 32, //22
		10, 27, 28, 24, 25, 26, 20, 32,  //31
		15, 29, 30, 31, 24, 25, 26, 20, 32 //39
	};
	static int[] c = {0, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 
			32, 34, 36, 38, 40, 13, 16, 19, 25, 30, 35, 22, 24, 28, 27, 26, 0};
	static int[] dice, temp, nowHorse;
	static boolean[] check;
	static int max;
	public static void main(String[] args) throws Exception{
		System.setIn(new FileInputStream("res/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		//input
		dice = new int[10];
		temp = new int[10];
		for(int i=0; i<10; i++) {
			dice[i] = Integer.parseInt(st.nextToken());
		}
//		System.out.println(c.length);
		max = Integer.MIN_VALUE;
//		permutation(0);
//		System.out.println(Arrays.toString(c));
		moveHorse(new int[] {1, 1, 1, 1, 2, 2, 2, 3, 3, 0});
		System.out.println(max);

	}
	private static void permutation(int cnt) {
		if(cnt == 10) {
			check = new boolean[33];
			moveHorse(temp);
//			System.out.println(Arrays.toString(temp));
			return;
		}
		for(int i=0; i<4; i++) {
			temp[cnt] = i;
			permutation(cnt+1);
		}
		
	}
	private static void moveHorse(int[] horseOrder) {
		//nowHorse : 각각의 말들의 위치
		//idx : 현재 말의 위치
		nowHorse = new int[4];
		System.out.println(Arrays.toString(dice));
		System.out.println(Arrays.toString(horseOrder));
		int result = 0;
		loop:for(int i=0; i<10; i++) {
			if (nowHorse[horseOrder[i]] == -1) {	
				max = Math.max(max, result);
				return;
			}
			System.out.print("nowHorse["+horseOrder[i]+"]"+ "="+ nowHorse[horseOrder[i]]);
			System.out.println(" :: dice["+i+"]"+ "="+ dice[i]);
			int idx = nowHorse[horseOrder[i]];	//현재 위치
			for(int j=0; j<dice[i]; j++) {	//주사위 굴러가는 도중에 도착지점 == -1로 표시하고 그 다음말로
				if(map[idx] == 32) {
					nowHorse[horseOrder[i]] = -1;
					continue loop;
				}
//				System.out.println(idx);
				idx++;
			}
//			System.out.println("idx = "+idx+ " :: i = "+ i);
			if(idx == 5) {
				idx = 22;
				System.out.println(idx);
			}
			else if(idx == 10) {
				idx = 31;
//				System.out.println(idx);
			}
			else if(idx == 15) {
				idx = 39;
//				System.out.println(idx);
			}
			for(int j=0; j<4; j++) {
				if(j == horseOrder[i]) continue;
				if(isSame(nowHorse[j], map[idx])) {
//					System.out.println(nowHorse[j] +" = "+map[idx]);
					return;
				}
			}
			nowHorse[horseOrder[i]] = idx;
			result += c[map[idx]];
			System.out.println("idx = "+idx+" :: map[idx] = "+map[idx]+" :: result = "+result);
			
		}
		max = Math.max(max, result);
	}
	private static boolean isSame(int now, int mapidx) {
		if((now == 5 || now == 22) && (mapidx == 5 || mapidx == 22)){
			return true;
		}
		//20, 29, 37, 46
		if((now == 20 || now == 29 || now == 37 || now == 46) && (mapidx == 20 || mapidx == 29 || mapidx == 37 || mapidx == 46)){
			return true;
		}
		//26, 34, 43
		if((now == 26 || now == 34 || now == 43 ) && (mapidx == 26 || mapidx == 34 || mapidx == 43)){
			return true;
		}
		//27, 35, 44
		if((now == 27 || now == 35 || now == 44 ) && (mapidx == 27 || mapidx == 35 || mapidx == 44)){
			return true;
		}
		//28, 36, 45
		if((now == 28 || now == 36 || now == 45) && (mapidx == 28 || mapidx == 36 || mapidx == 45)){
			return true;
		}
		//10, 31
		if((now == 10 || now == 31) && (mapidx == 10 || mapidx == 31)){
			return true;
		}
		//15, 39
		if((now == 15 || now == 39) && (mapidx == 15 || mapidx == 39)){
			return true;
		}
		return false;
		
	}

	

}
