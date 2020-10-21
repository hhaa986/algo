//201010 아기상어
package baekjoon;

import java.io.*;
import java.util.*;

public class Main16236 {
	/**
	 * 아기상어 처음크기 2
	 * - 자기 크기의 수만큼 물고기 먹으면 크기 증가
	 * 상하좌우 이동
	 * - 자기보다 큰 물고기 칸 지나갈 수 X
	 * - 자기보다 작은 물고기 먹는다 O
	 * - 같은 크기는 지나갈 수 O / 먹지는 X
	 * 
	 * 이동
	 * - 먹을 수 없는 물고기가 공간에 없다 -> 끝
	 * - 먹을 물고기 1마리 -> 거기로 간다.
	 * - 먹을 물고기 1마리 이상 -> 가장 가까운 칸  , 가장 위, 가장 왼쪽
	 * 
	 * input) N / Map 
	 * output) 몇초동안 도움요청하지 않고 물고기를 잡아먹을 수 있는지 구하기.
	 * */
	//	static int N;
	//	static Shark nowShark;
	public static void main(String[] args) throws Exception{
		System.setIn(new FileInputStream("res/input.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		//input
		int N = Integer.parseInt(st.nextToken());
		Shark shark = new Shark();
		int[][] map = new int[N][N];
		for(int i=0; i<N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == 9) {
					shark = new Shark(i, j, 2);
					map[i][j] = 0;
				}
			}
		}

		//solve
		List<Shark> fish = new ArrayList<>();
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(map[i][j] != 0) {
					int dist = checkDistance(shark, i, j);
					fish.add(new Shark(i, j, map[i][j], dist));
				}
			}
		}
//		System.out.println(shark.x+" "+shark.y+" "+shark.size);
//		System.out.println("Fish List");
//		for(int i=0; i<fish.size(); i++) System.out.println(fish.get(i).x+" "+fish.get(i).y+" "+fish.get(i).size+" "+fish.get(i).distance);
		int result = 0;
		int eatShark = 0;
		while(!fish.isEmpty()) {
			Collections.sort(fish, comp);
			if(checkFishSize(shark.size, fish)) break;
			result += fish.get(0).distance;
			shark.x = fish.get(0).x;
			shark.y = fish.get(0).y;
			eatShark++;
			fish.remove(0);
			if(shark.size == eatShark) {
				shark.size++;
				eatShark = 0;
			}
			for(int k=0; k<fish.size(); k++) {
				int dist = checkDistance(shark, fish.get(k).x, fish.get(k).y);
				int[] tmp = {fish.get(k).x, fish.get(k).y, fish.get(k).size};
				fish.set(k, new Shark(tmp[0], tmp[1], tmp[2], dist));
			}
		}



		//output
		System.out.println("res : " + result);

	}

	private static boolean checkFishSize(int size, List<Shark> fish) {
		for(int i=0; i<fish.size(); i++) {
			if(fish.get(i).size < size) {
				return false;				
			}
		}
		return true;
	}

	private static int checkDistance(Shark sha, int x, int y) {
		return Math.abs(sha.x - x) + Math.abs(sha.y - y);
	}

	static Comparator<Shark> comp = (o1, o2) ->{
		if(o1.distance == o2.distance) {
			if(o1.x == o2.x) {
				return o1.y - o2.y;
			}
			else {
				return o1.x - o2.x;
			}
		}
		else {
			return o1.distance - o2.distance;					
		}
	};
	
	static class Shark{
		int x;
		int y;
		int size;
		int distance;

		public Shark(int x, int y, int size, int distance) {
			this.x = x;
			this.y = y;
			this.size = size;
			this.distance = distance;
		}
		public Shark(int x, int y, int size) {
			this.x = x;
			this.y = y;
			this.size = size;
		}
		public Shark() {}
	}

}
