package sample.junit5;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class UnUseIf {

    private boolean getType(Article article) {
        // 記事タイプでの比較
        if (article.getType().equals("news")) {
            return true;
        } else if (article.getType().equals("entertainment")) {
            return false;
        } else if (article.getType().equals("recipe")) {
            return false;
        } else {
            return false;
        }
    }

    private static final Map<String, Boolean> TYPE_MAP = Map.of("news", true, "entertainment", false, "recipe", false);

    private boolean getType2(Article article) {
        if (!TYPE_MAP.containsKey(article.getType())) {
            throw new IllegalArgumentException("invalid value.");
        }
        return TYPE_MAP.get(article.getType());
    }

    @Test
    void test() {
        Article article = new Article("news");
        System.out.println("type: " + getType(article));
        System.out.println("type: " + getType2(article));
    }

}
