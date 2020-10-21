//201017 어른상어                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   어른상어
package baekjoon;

import java.io.*;
import java.util.*;

public class Main19237 {
	static int N, M, K, shark_cnt, result, imap[][], nowShark[][], sharkNowDir[];
	static Smell[][] smap;
	static int dirX[] = {-1,1,0,0}, dirY[] = {0,0,-1,1};
	public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("res/input.txt"));
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        //input
        N = Integer.parseInt(st.nextToken());	//격자 길이
        M = Integer.parseInt(st.nextToken());	//상어 수
        K = Integer.parseInt(st.nextToken());	//냄새 사라지는 시간
        result = 0;
        smap = new Smell[N][N];
        imap = new int[N][N];
        nowShark = new int[M][2];
        sharkNowDir = new int[M];
        shark_cnt = M;
        int[][][] sharkDir = new int[M][4][4]; 	// 상어 우선순위 방향 = 방향 0,1,2,3
        for(int i=0; i<N; i++) {
        	st = new StringTokenizer(br.readLine());
        	for(int j=0; j<N; j++) {
        		imap[i][j] = Integer.parseInt(st.nextToken()) - 1;
        		if(imap[i][j] > -1) {
        			nowShark[imap[i][j]][0] = i;
        			nowShark[imap[i][j]][1] = j;
        		}
        		smap[i][j] = new Smell(-1,0);
        	}
        }
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<M; i++) {	//상어 idx 0부터 시작 , 방향도 0부터
        	sharkNowDir[i] = Integer.parseInt(st.nextToken()) - 1;
        }
        
        for(int i=0; i<M; i++) {
        	for(int j=0; j<4; j++) {
        		st = new StringTokenizer(br.readLine());
        		for(int k=0; k<4; k++) {
        			sharkDir[i][j][k] = Integer.parseInt(st.nextToken()) -1;
        		}
        	}
        }
       
        
        //solve
        for(int time=0; time<1000; time++) {
        	//냄새 시간 -1씩 --> 0일경우 idx -1로 바꾸기
        	if(time> 0) {
	        	 for(int i=0; i<N; i++) {
	             	for(int j=0; j<N; j++) {
	             		if(smap[i][j].t > 0) {
	             			smap[i][j].t--;
	             			if(smap[i][j].t == 0) smap[i][j].idx = -1;
	             		}
	             	}
	             }
        	}
        	
        	//현재 상어 위치에 냄새 뿌리기 smap에 imap인덱스의 냄새 뿌리기
        	for(int i=0; i<N; i++) {
        		for(int j=0; j<N; j++) {
        			if(imap[i][j] > -1) {
        				smap[i][j].idx = imap[i][j];
        				smap[i][j].t = K;
        			}
        		}
        	}
        	
        	//이동
        	//1. 인접칸 중 아무냄새 없는곳 탐색 ->List에 담기 nowShark :현재 상어 위치
        	Shark shark = null;
        	int[][] tmpMap = new int[N][N];
        	for(int i=0; i<N; i++) {
        		for(int j=0; j<N; j++) {
        			tmpMap[i][j] = -1;
        		}
        	}
        	loop:for(int i=0; i<M; i++) {	//상어 한마리씩 탐색
        		if(nowShark[i][0] == -1 && nowShark[i][1] == -1) continue; //사라진 상어는 바이
        		
        		int[] dd = sharkDir[i][sharkNowDir[i]];
        		for(int d=0; d<4; d++) {
        			int x = nowShark[i][0] + dirX[dd[d]];
        			int y = nowShark[i][1] + dirY[dd[d]];
        			if(isMap(x, y) && smap[x][y].idx == -1) {
        				shark = new Shark(x, y, i, dd[d]); //x, y, idx, dir(이동할 방향)
        				if(tmpMap[x][y] > -1) { //누군가 있음 --> 자기 자신 삭제
        					nowShark[i][0] = -1;
        					nowShark[i][1] = -1;
        					shark_cnt--;
        				} 
        				else {
        					nowShark[i][0] = x;
        					nowShark[i][1] = y;
        					sharkNowDir[i] = shark.dir;
        					tmpMap[x][y] = shark.idx;
        				}
        				continue loop;
        			}
        		}
    			//만약 인접칸 중 아무냄새 없는곳이없으면 -> 자기냄새 찾기 
        		for(int d=0; d<4; d++) {
        			int x = nowShark[i][0] + dirX[dd[d]];
        			int y = nowShark[i][1] + dirY[dd[d]];
        			if(isMap(x, y) && smap[x][y].idx == i) {
        				shark = new Shark(x, y, i, dd[d]); //x,y,idx, dir(이동할 방향)
        				if(tmpMap[x][y] > -1) { //누군가 있음 --> 자기 자신 삭제
        					nowShark[i][0] = -1;
        					nowShark[i][1] = -1;
        					shark_cnt--;
        				} 
        				else {
        					nowShark[i][0] = x;
        					nowShark[i][1] = y;
        					sharkNowDir[i] = shark.dir;
        					tmpMap[x][y] = shark.idx;
        				}
        				continue loop;
        			}
        		}
        	}//상어 인덱스 끝
        	for(int i=0; i<N; i++) {
        		for(int j=0; j<N; j++) {
        			imap[i][j] = tmpMap[i][j];
        		}
        	}
        	
        	result++;
        	if(shark_cnt == 1) {
        		break;
        	}
        }
        
        //output
       if(shark_cnt > 1) {
    	   result = -1;
       }
    	System.out.println(result);
      
       
	}
    private static boolean isMap(int x, int y) {
		if(x>=0 && y>=0 && x<N && y<N) return true;
		else return false;
	}
	
	static class Shark {
    	int idx;
    	int dir;
    	int x;
    	int y;
    	
    	Shark(){}
    	Shark(int x, int y, int idx, int dir){
    		this.x = x;
    		this.y = y;
    		this.idx = idx;
    		this.dir = dir;
    	}
    	Shark(int idx, int dir){
    		this.idx = idx;
    		this.dir = dir;
    	}
    }
    static class Smell{
    	int idx;
    	int t;
    	Smell(){
    	}
    	Smell(int idx, int t){
    		this.idx = idx;
    		this.t = t;
    	}
    }

}
