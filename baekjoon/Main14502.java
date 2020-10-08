// 201007 연구소
package baekjoon;

import java.io.*;
import java.util.*;

public class Main14502 {
    private static int N, M, result;
    private static int[] wallX, wallY;
    private static int[] dirX = {-1, 1, 0, 0}, dirY = {0, 0, -1, 1}; //상 하 좌 우
    private static boolean[] check;
    private static int[][] map;
    private static ArrayList<int[]> zero;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        //input
        N = Integer.parseInt(st.nextToken());    //행
        M = Integer.parseInt(st.nextToken());    //열
        map = new int[N][M];
        zero = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 0) zero.add(new int[]{i, j});
            }
        }
        check = new boolean[zero.size()];

        wallX = new int[3];
        wallY = new int[3];

        //풀이
        result = 0;
        wallCheck(0, 0);

        //출력
        System.out.println(result);

    }

    // 벽 세우기 --> 바이러스 확산 반복
    private static void wallCheck(int count, int begin) {
        if (count == 3) {
            for (int m = 0; m < 3; m++) map[wallX[m]][wallY[m]] = 1;
            bfs();
            for (int m = 0; m < 3; m++) map[wallX[m]][wallY[m]] = 0;
            return;
        }
        for (int k = begin; k < zero.size(); k++) {
            if (check[k]) continue;
            check[k] = true;
            wallX[count] = zero.get(k)[0];
            wallY[count] = zero.get(k)[1];
            wallCheck(count + 1, k + 1);
            check[k] = false;
        }
    }
    
    // 확산
    private static void bfs() {
        boolean[][] bvisited = new boolean[N][M];
        Queue<int[]> q = new LinkedList<>();
        
        // 바이러스를 확산하기 위한 임시 2차원 배열
        int[][] temp = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                temp[i][j] = map[i][j];
                if (temp[i][j] == 2) q.offer(new int[]{i, j});
            }
        }
        // 바이러스 확산
        while (!q.isEmpty()) {
            int[] curr = q.poll();
            bvisited[curr[0]][curr[1]] = true;
            for (int d = 0; d < 4; d++) {
                int xx = curr[0] + dirX[d];
                int yy = curr[1] + dirY[d];
                if (xx >= 0 && yy >= 0 && xx < N && yy < M && !bvisited[xx][yy] && temp[xx][yy] == 0) {
                    temp[xx][yy] = 2;
                    bvisited[xx][yy] = true;
                    q.offer(new int[]{xx, yy});
                }
            }
        }
        // 안전영역 세기
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (temp[i][j] == 0) cnt++;
            }
        }
        // 최대 크기 비교
        if (cnt > result) result = cnt;
    }
}
