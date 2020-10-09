//201009 캐슬디펜스
package baekjoon;

import java.io.*;
import java.util.*;

/**
 * 궁수 3명
 * 궁수 위치 : N+1번행
 * Map = N * M(행 x 열)
 * <p>
 * 궁수 한번에 공격
 * D이하 중 가장 가까운 적
 * 적이 여럿 - 가장 왼쪽에 있는 적
 * 공격 당하면 아웃
 * 공격 받은 후 한칸 아래 이동
 * <p>
 * input : N M D(궁수 사정 거리)
 * output : 적의 최대 수
 */
public class Main17135 {
    static int N, M, D, max, map[][], archerData[];
    static boolean check[];
    static List<int[]> enemy, archer1, archer2, archer3;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        //input
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        D = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        enemy = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] == 1) enemy.add(new int[]{i, j});
            }
        }
        //solve
        archerData = new int[3];
        check = new boolean[M];
        max = Integer.MIN_VALUE;
        getTreeArcher(0, 0);

        //output
        System.out.println(max);

    }

    private static void getTreeArcher(int idx, int begin) {
        if (idx == 3) {
            all(archerData);
            return;
        }
        for (int i = begin; i < M; i++) {
            if (check[i]) continue;
            check[i] = true;
            archerData[idx] = i;
            getTreeArcher(idx + 1, i + 1);
            check[i] = false;
        }
    }

    private static void all(int[] data) {
        ArrayList<int[]> en = new ArrayList<>(enemy);
        Collections.copy(en, enemy);
        int cnt = 0;
        while (!en.isEmpty()) {
            archer1 = new ArrayList<>();
            archer2 = new ArrayList<>();
            archer3 = new ArrayList<>();
            for (int i = 0; i < en.size(); i++) {
                if (checkDistance(en.get(i)[0], en.get(i)[1], data[0]) <= D) {
                    archer1.add(new int[]{checkDistance(en.get(i)[0], en.get(i)[1], data[0]), en.get(i)[1], i});
                }
                if (checkDistance(en.get(i)[0], en.get(i)[1], data[1]) <= D) {
                    archer2.add(new int[]{checkDistance(en.get(i)[0], en.get(i)[1], data[1]), en.get(i)[1], i});
                }
                if (checkDistance(en.get(i)[0], en.get(i)[1], data[2]) <= D) {
                    archer3.add(new int[]{checkDistance(en.get(i)[0], en.get(i)[1], data[2]), en.get(i)[1], i});
                }
            }
            archer1.sort(comp);
            archer2.sort(comp);
            archer3.sort(comp);

            //삭제할 idx 추가
            Set<Integer> hs = new HashSet<>();
            if (!archer1.isEmpty()) hs.add(archer1.get(0)[2]);
            if (!archer2.isEmpty()) hs.add(archer2.get(0)[2]);
            if (!archer3.isEmpty()) hs.add(archer3.get(0)[2]);

            //삭제
            List<Integer> hsList = new ArrayList<>(hs);
            hsList.sort((o1, o2) -> o2 - o1);
            for (Integer integer : hsList) {
                en.remove((int) integer);
                cnt++;
            }
            int size = en.size();
            for (int i = size - 1; i >= 0; i--) {
                int tmpX = en.get(i)[0] + 1;
                int tmpY = en.get(i)[1];
                if (tmpX == N) en.remove(i);
                else {
                    en.set(i, new int[]{tmpX, tmpY});
                }
            }
        }//while is end
        max = Math.max(max, cnt);
    }

    private static int checkDistance(int x, int y, int archerY) {
        int result = 0;
        result += Math.abs(N - x);
        result += Math.abs(archerY - y);
        return result;
    }

    static Comparator<int[]> comp = (o1, o2) -> {
        if (o1[0] == o2[0])
            return o1[1] - o2[1];
        else
            return o1[0] - o2[0];
    };
}
