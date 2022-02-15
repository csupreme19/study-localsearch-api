package com.api.utils;

import org.springframework.stereotype.Component;

/**
 * 알고리즘 코드를 사용하기 위한 유틸리티
 * 
 * @author csupreme19
 * @since 2022.02.15
 */

@Component
public class AlgorithmUtils {

	// 레빈슈타인 거리 알고리즘
	// 두 문자열의 유사도를 확인한다.
	public static int levinshteinDistance(String str1, String str2) {
		str1 = "-"+str1;
		str2 = "-"+str2;
		int m = str1.length();
		int n = str2.length();
		int[][] distance = new int[m][n];
		for(int i=0; i<m; i++) distance[i][0] = i;
		for(int j=0; j<n; j++) distance[0][j] = j;

		for(int i=1; i<m; i++) {
			for(int j=1; j<n; j++) {
				distance[i][j] = Math.min(
						Math.min(
								distance[i][j-1]+1				// str1의 이전 문자열에서 삽입/삭제
								, distance[i-1][j]+1)			// str2의 이전 문자열에서 삽입/삭제
						, str1.charAt(i) == str2.charAt(j) ? distance[i-1][j-1] : distance[i-1][j-1]+1);	// str1, 2의 이전 문자열에서 삽입/삭제 또는 안함 
			}
		}

		//		print(distance);

		return distance[m-1][n-1];
	}

	private static void print(int[][] distance) {
		for(int i=0; i<distance.length; i++) {
			for(int j=0; j<distance[0].length; j++) {
				System.out.print(distance[i][j] + " ");
			}
			System.out.println();
		}
	}
}
