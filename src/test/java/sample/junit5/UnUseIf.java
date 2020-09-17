package sample.junit5;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class UnUseIf {

    private static int getTypeId(Article article) {
        // 記事タイプでの比較
        if (article.getType().equals("news")) {
            return 1;
        } else if (article.getType().equals("entertainment")) {
            return 2;
        } else if (article.getType().equals("recipe")) {
            return 3;
        } else {
            throw new IllegalArgumentException("Invalid type. type: " + article);
        }
    }

    private static final Map<String, Integer> TYPE_ID_MAP = Map.of("news", 1, "entertainment", 2, "recipe", 3);

    private static int getTypeId2(Article article) {
        if (!TYPE_ID_MAP.containsKey(article.getType())) {
            throw new IllegalArgumentException("Invalid type. type: " + article);
        }
        return TYPE_ID_MAP.get(article.getType());
    }

    @Test
    void test() {
        Article article = new Article("news");
        System.out.println("typeId: " + getTypeId(article));
        System.out.println("typeId: " + getTypeId2(article));
    }

}
