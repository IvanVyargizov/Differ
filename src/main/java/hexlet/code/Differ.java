package hexlet.code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class Differ {

    public static String generate(String path1, String path2, String outputFormat) throws IOException {
        Map<String, Object> fileContent1
                = Parser.parse(Files.readString(Path.of(castAbsolutePath(path1))).trim(), getFileFormat(path1));

        Map<String, Object> fileContent2
                = Parser.parse(Files.readString(Path.of(castAbsolutePath(path2))).trim(), getFileFormat(path2));

        return Formatter.output(generateDiff(fileContent1, fileContent2), outputFormat);
    }

    public static String generate(String path1, String path2) throws IOException {
        final String defaultFormat = "stylish";
        return generate(path1, path2, defaultFormat);
    }

    private static List<Map<String, Object>> generateDiff(Map<String, Object> content1, Map<String, Object> content2) {
        TreeSet<String> mergingKeyContent = new TreeSet<>(content1.keySet());
        mergingKeyContent.addAll(content2.keySet());
        List<Map<String, Object>> diff = new ArrayList<>();
        mergingKeyContent.forEach(
                (k) -> {
                    if (!content1.containsKey(k)) {
                        Map<String, Object> valueMap = new HashMap<>();
                        valueMap.put("status", "ADDED");
                        valueMap.put("fieldName", k);
                        valueMap.put("value1", "");
                        valueMap.put("value2", content2.get(k));
                        diff.add(valueMap);
                    } else if (!content2.containsKey(k)) {
                        Map<String, Object> valueMap = new HashMap<>();
                        valueMap.put("status", "REMOVED");
                        valueMap.put("fieldName", k);
                        valueMap.put("value1", content1.get(k));
                        valueMap.put("value2", "");
                        diff.add(valueMap);
                    } else if (!Objects.equals(content1.get(k), content2.get(k))) {
                        Map<String, Object> valueMap = new HashMap<>();
                        valueMap.put("status", "CHANGED");
                        valueMap.put("fieldName", k);
                        valueMap.put("value1", content1.get(k));
                        valueMap.put("value2", content2.get(k));
                        diff.add(valueMap);
                    } else {
                        Map<String, Object> valueMap = new HashMap<>();
                        valueMap.put("status", "UNCHANGED");
                        valueMap.put("fieldName", k);
                        valueMap.put("value1", content1.get(k));
                        valueMap.put("value2", content2.get(k));
                        diff.add(valueMap);
                    }
                }
        );
        return diff;
    }

    private static String getFileFormat(String path) {
        return path.substring(path.lastIndexOf("."));
    }

    //По замечаниям к проекту указано, что можно просто использовать Path#toAbsolutePath
    //Но мой метод castAbsolutePath позволяет находит файл по всей системе
    //В то время как метод Path#toAbsolutePath переданный путь приводит к абсолютному пути до директории проекта
    //И если файл существует, но лежит не в директории проекта, то будет выброшено исключение
    //Прошу меня поправить, если я ошибаюсь или для проекта обработка нахождения файла по всей системе излишне
    public static String castAbsolutePath(String path) throws IOException {
        if (Paths.get(path).isAbsolute()) {
            return path;
        }
        Path rootPath = Paths.get(path).toAbsolutePath().getRoot().resolve(Paths.get(path).toAbsolutePath().getName(0));
        return Files.find(rootPath,
                Integer.MAX_VALUE,
                (p, basicFileAttributes) ->
                        p.endsWith(Paths.get(path)))
                .collect(Collectors.toList()).toString()
                .replaceAll("\\[", "")
                .replaceAll("]", "");
    }

}
