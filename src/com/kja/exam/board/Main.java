package com.kja.exam.board;

import java.util.*;

public class Main {
    static void makeTestData(List<Article> articles) {
        // 테스트 데이터 3개 등록, 시작
        for (int i = 0; i < 100; i++) {
            int id = i + 1;
            articles.add(new Article(id, "제목" + id, "내용" + id));
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("== 게시판 v 0.1 ==");
        System.out.println("== 프로그램 시작 ==");

        int articlesLastId = 0;
        ArrayList<Article> articles = new ArrayList<>();

        makeTestData(articles);

        if (articles.size() > 0) {
            articlesLastId = articles.get(articles.size() - 1).id;
        }

        while (true) {
            System.out.printf("명령) ");
            String cmd = sc.nextLine();

            Rq rq = new Rq(cmd);
            Map<String, String> params = rq.getParams();

            if (rq.getUrlPath().equals("exit")) {
                break;
            } else if (rq.getUrlPath().equals("/usr/article/list")) {
                System.out.println("- 게시물 리스트 -");
                System.out.println("--------------------");
                System.out.println("번호 / 제목");
                System.out.println("--------------------");

                List<Article> sortedArticles = articles;

                boolean orderByIdDesc = true;

                if (params.containsKey("orderBy") && params.get("orderBy").equals("idAsc")) {
                    orderByIdDesc = false;
                }

                if (orderByIdDesc) {
                    sortedArticles = Util.reverseList(sortedArticles);
                }

                for (Article article : sortedArticles) {
                    System.out.printf("%d / %s\n", article.id, article.title);
                }

            } else if (rq.getUrlPath().equals("/usr/article/detail")) {

                // /usr/article/detail?id=3 입력하면 id가 3인 페이지 노출

                if (params.containsKey("id") == false) {
                    System.out.println("id를 입력해주세요.");
                    continue;
                }

                int id = 0;

                try {
                    id = Integer.parseInt(params.get("id"));
                } catch (NumberFormatException e) {
                    System.out.println("id를 정수형태로 입력해주세요.");
                    continue;
                }

                if (id > articles.size()) {
                    System.out.println("게시물이 존재하지 않습니다.");
                    continue;
                }

                Article article = articles.get(id - 1);

                System.out.println("- 게시물 상세내용 -");
                System.out.printf("번호 : %d\n", article.id);
                System.out.printf("제목 : %s\n", article.title);
                System.out.printf("내용 : %s\n", article.body);

            } else if (rq.getUrlPath().equals("/usr/article/write")) {
                System.out.println("- 게시물 등록 -");
                System.out.printf("제목 : ");
                String title = sc.nextLine();
                System.out.printf("내용 : ");
                String body = sc.nextLine();

                int id = articlesLastId + 1;
                articlesLastId = id;

                Article article = new Article(id, title, body);
                articles.add(article);
                System.out.println("생성된 게시물 객체 : " + article);

                System.out.printf("%d번 게시물이 입력되었습니다.\n", article.id);

            } else {
                System.out.printf("입력된 명령어 : %s\n", cmd);
            }
        }
        System.out.println("== 프로그램 종료 ==");

        sc.close();
    }
}


class Article {
    int id;
    String title;
    String body;

    Article(int id, String title, String body) {
        this.id = id;
        this.title = title;
        this.body = body;
    }

    @Override
    public String toString() {
        return String.format("{id: %d, title: \"%s\", body: \"%s\"}", id, title, body);
    }
}

class Rq {
    private String url;
    private String urlPath;
    private Map<String, String> params;


    Rq(String url) {
        this.url = url;
        urlPath = Util.getUrlPathFromUrl(this.url);
        params = Util.getParamsFromUrl(this.url);
    }

    public Map<String, String> getParams() {
        return params;
    }

    public String getUrlPath() {
        return urlPath;
    }
}


class Util {

    // 이 함수는 원본리스트를 훼손하지 않고, 새 리스트를 생성
    // 즉 정렬이 반대인 복사본리스트를 생성해 반환
    public static <T> List<T> reverseList(List<T> list) {
        List<T> reverse = new ArrayList<>(list.size());

        for (int i = list.size() - 1; i >= 0; i--) {
            reverse.add(list.get(i));
        }
        return reverse;
    }

    static Map<String, String> getParamsFromUrl(String url) {
        Map<String, String> params = new HashMap<>();
        String[] urlBits = url.split("\\?", 2);

        if (urlBits.length == 1) {
            return params;
        }

        String queryStr = urlBits[1];
        for (String bit : queryStr.split("&")) {
            String[] bits = bit.split("=", 2);
            if (bits.length == 1) {
                continue;
            }
            params.put(bits[0], bits[1]);
        }

        return params;
    }

    static String getUrlPathFromUrl(String url) {
        return url.split("\\?", 2)[0];
    }
}