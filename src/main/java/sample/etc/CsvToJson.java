package sample.etc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CsvToJson {
    public static void main(String[] args) {
        System.out.println("hoge");
        Path csvPath = Paths
                .get("/home/vagrant/dev/github.com/yukkun007/gradle-test/src/main/java/sample/etc/hoge.csv");
        csvToJson(csvPath);
    }

    /**
     * CSVファイルの全行をJSON形式に変換する.
     */
    public static void csvToJson(Path csvPath) {

        // csvファイル読込
        File csv = csvPath.toFile();
        try (BufferedReader br = new BufferedReader(new FileReader(csv))) {

            // 先頭行はカラム名
            final String[] header = br.readLine().split(",");
            List<String> jsonRecords = new LinkedList<>();

            loop: while (true) {

                // 全行取得
                String record = br.readLine();
                if (record == null)
                    break loop;
                String[] column = record.split(",");

                // {"header0":"column0","header1":"column1"}という形に成形
                String jsonRecord = "{" + IntStream.range(0, header.length).boxed()
                        .map(v -> "\"" + header[v] + "\":\"" + column[v] + "\"").collect(Collectors.joining(",")) + "}";

                jsonRecords.add(jsonRecord);
            }

            // [{"header0":"0","header1":"1"},{"header0":"2","header1":"3"}]という形に接続
            String json = "[" + String.join(",", jsonRecords) + "]";

            System.out.println(json);

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
