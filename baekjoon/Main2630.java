//201020 색종이만들기
package baekjoon;

import java.io.*;
import java.util.*;
public class Main2630 {
	/**
	 * 전체 종이의 크기 N*N 
	 * 모두 같은색으로 칠해져 있지 않으면 가로와 세로의 중간부분을 자른다.=>N/2크기의 색종이4개
	 * 이걸 반복해서 잘라진 종이가 모두 같은색일경우 그때의 흰색종이 개수,파란색종이 개수를 출력
	 * 
	 * input) 
	 * 첫줄 - 한변의 길이 N(2의 제곱승 중 하나)
	 * 그다음 - 색종이 크기(하얀색-0/파란색-1)
	 * 
	 * output)
	 * 첫줄-잘라진 하얀색 색종이의 개수 출력
	 * 둘째줄-파란색 색종이의 개수 출력 
	 * 
	 * 풀이)
	 * 1. 먼저 입력을 받아야겠지..
	 * 2. papercut 함수를 만든다.(그 색종이의 시작이 되는 x값, y값, papaersize)
	 * 3. 처음에 현재 있는 종이가 하나의 색이 있는 종이로 판별되지 않을경우 4개의 papercut함수로 들어간다.
	 * 3-1. 색은 그 범위의 map을 돌면서 모두 같은경우일때 true를 리턴/그렇지 않을 경우 false를 리턴하는 함수.
	 * 3-2. 두가지색 check의 리턴값이 모두 false 일경우 4범위로 나눠서 papercut 함수로 들어간다.
	 * 3-3. 이때의 범위는 맨 처음 들어오는 papersize를 이용해서 만든다.
	 * 4. 최종으로 하얀색종이의 수와 파란색종이의 수를 출력
	 * */
	private static int N,Wcnt,Bcnt;
	private static int[][] paper;
	
	public static void main(String[] args) throws Exception {
	//	System.setIn(new FileInputStream("res/input.txt"));
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		//입력
		N=Integer.parseInt(br.readLine());
		paper=new int[N][N];
		String s=new String();
		for(int i=0;i<N;i++) {
			s=br.readLine();
			for(int j=0;j<N;j++) {
				paper[i][j]=s.charAt(2*j)-'0';
			}
		}
		//입력확인
		for(int i=0;i<N;i++) {
			for(int j=0;j<N;j++) {
				System.out.print(paper[i][j]);
			}
			System.out.println();
		}
		//풀이
		paperCut(0,0,N);
		
		//출력
		System.out.println(Wcnt);
		System.out.println(Bcnt);
	}

	private static void paperCut(int x, int y, int paperSize) {
		if(!Bcheck(x,y,paperSize)&&!Wcheck(x,y,paperSize)) {
			//각각의 종이가 시작되는 x,y 좌표와 그 papersize를 넘긴다.
			paperCut(x,y,paperSize/2); 							//1사분면
			paperCut(x+paperSize/2,y,paperSize/2);				//2사분면
			paperCut(x,y+paperSize/2,paperSize/2);				//3사분면
			paperCut(x+paperSize/2,y+paperSize/2,paperSize/2);	//4사분면
		}
	}
	private static boolean Bcheck(int x, int y, int paperSize) {//파란색종이 체크
		for(int i=x;i<x+paperSize;i++) {
			for(int j=y;j<y+paperSize;j++) {				//맵을 돌면서 확인
				if(paper[i][j]!=1) return false;			//만약 하나라도 1이 아니면 false리턴
			}
		}
		Bcnt++;			//위에서 걸리지 않았다면->파란색종이가 맞기때문에 ++
		return true;	//true 리턴
	}
	private static boolean Wcheck(int x, int y, int paperSize) {//하얀색종이 체크
		for(int i=x;i<x+paperSize;i++) {
			for(int j=y;j<y+paperSize;j++) {				//맵을 돌면서 확인
				if(paper[i][j]!=0) return false;			//만약 하나라도 1이 아니면 false리턴
			}
		}
		Wcnt++;			//위에서 걸리지 않았다면->흰색종이가 맞기때문에 ++
		return true;	//true 리턴
	}
}