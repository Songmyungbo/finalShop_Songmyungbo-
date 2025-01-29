package util;

import java.util.InputMismatchException;

import java.util.Scanner;

public class Util {
	private static Scanner sc = new Scanner(System.in);
	public static int getValue(String msg, int start, int end) {
		int num = 0;
		while(true) {
			try {
				System.out.print(msg);
				System.out.printf("[%d-%d] 입력: ",start,end);
				num = sc.nextInt();
				if(num < start || num > end) {
					System.out.println("입력을 다시하세요");
					continue;
				}
				return num;
			}catch(InputMismatchException e) {
				System.out.println("숫자만 입력 가능합니다");
				sc.nextLine();
				continue;
			}
		}
	}

	public static String getValue(String msg) {
		System.out.println(msg +" 입력: ");
		String valuse = sc.next();
		sc.nextLine();
		
		return valuse;
	}

}
