package sample.stream;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class StreamTest {

    @Test
    public void hogeTest() {
        HashMap<String, Object> map1 = new HashMap<String, Object>();
        map1.put("language", "ja");
        map1.put("data", Arrays.asList("part1", "part2"));

        HashMap<String, Object> map2 = new HashMap<String, Object>();
        map2.put("language", "en");
        map2.put("data", Arrays.asList("part3", "part4"));

        List<HashMap<String, Object>> list = Arrays.asList(map1, map2);

        // 独自Mapを通常のMapに変換
        List<HashMap<String, Object>> test = list.stream().map(item -> item).collect(Collectors.toList());
        // MapのItemを特定のkey/valueでfilter、さらにデータを取得して最初の要素をとる
        Object test2 = test.stream().filter(item -> item.containsKey("language") && item.containsValue("ja"))
                .map(item -> item.get("data")).findFirst().get();
        // これがリストになっているはず
        System.out.println(test2);

    }
}
