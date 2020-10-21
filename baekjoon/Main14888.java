// 201008 
package baekjoon;

import java.io.*;
import java.util.*;

public class Main14888 {
    private static int N, min, max, num[], operator[], temp[];
    private static boolean bOperator[];

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        //input
        N = Integer.parseInt(st.nextToken());
        num = new int[N];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            num[i] = Integer.parseInt(st.nextToken());
        }
        operator = new int[N - 1];
        st = new StringTokenizer(br.readLine());
        int idx = 0;
        for (int i = 0; i < 4; i++){
            int opTmp = Integer.parseInt(st.nextToken());
            for(int j = 0; j < opTmp; j++){
                operator[idx] = i;
                idx++;
            }
        }
//        System.out.println(Arrays.toString(num));
//        System.out.println(Arrays.toString(operator));

        //solve
        min = Integer.MAX_VALUE;
        max = Integer.MIN_VALUE;
        Arrays.sort(operator);
        temp = new int[N -1];
        bOperator = new boolean[N - 1];
        dfs(0);

        //output
        System.out.println(max);
        System.out.println(min);
    }

    private static void dfs(int idx) {
        if(idx == N - 1){
            int res = cal(temp);
            max = Integer.max(res, max);
            min = Integer.min(res, min);
            return;
        }
        for(int k=0; k<N-1; k++){
            if(!bOperator[k]){
                bOperator[k] = true;
                temp[idx] = operator[k];
                dfs(idx+1);
                bOperator[k] = false;
            }
        }
    }

    private static int cal(int[] temp) {
        int result = num[0];
        for(int i = 0; i < N - 1; i++) {
            int op = temp[i];
            switch (op) {
                case 0:
                    result += num[i + 1];
                    break;
                case 1:
                    result -= num[i + 1];
                    break;
                case 2:
                    result *= num[i + 1];
                    break;
                case 3:
                    result /= num[i + 1];
                    break;
            }
        }
        return result;
    }
}