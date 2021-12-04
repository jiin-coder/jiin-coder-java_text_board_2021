package com.kja.exam.board;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("== 게시판 v 0.1 ==");
        System.out.println("== 프로그램 시작 ==");

        int articlesLastId = 0;

        while (true) {
            System.out.printf("명령) ");
            String cmd = sc.nextLine();

            if (cmd.equals("exit")) {
                break;
            } else if (cmd.equals("/usr/article/write")) {
                System.out.println("- 게시물 등록 -");
                System.out.printf("제목 : ");
                String title = sc.nextLine();
                System.out.printf("내용 : ");
                String body = sc.nextLine();

                int id = articlesLastId + 1;
                articlesLastId = id;

                System.out.printf("%d번 게시물이 입력되었습니다.\n", id);
            } else {
                System.out.printf("입력된 명령어 : %s\n", cmd);
            }

        }
        System.out.println("== 프로그램 종료 ==");

        sc.close();
    }
}
