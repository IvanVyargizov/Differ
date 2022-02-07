package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Json {

    public static String format(List<Map<String, Object>> diff) throws IOException {

        // я не совсем понял замечение, что можно сразу сериализовать при помощи ObjectMapper,
        // без дополнительного форматирования.
        // я не могу просто вернуть new ObjectMapper().writeValueAsString(diff)
        // мне по заданию нужно реализовать вывод формата "+ chars2: false"
        // поэтому перед передачей в ObjectMapper я провожу предварительное форматирование

        Map<String, String> formatting = new LinkedHashMap<>();
        for (Map<String, Object> node : diff) {
            switch (node.get("status").toString()) {
                case "ADDED" -> formatting.put("+ " + node.get("fieldName"), Objects.toString(node.get("value2")));
                case "REMOVED" -> formatting.put("- " + node.get("fieldName"), Objects.toString(node.get("value1")));
                case "CHANGED" -> {
                    formatting.put("- " + node.get("fieldName"), Objects.toString(node.get("value1")));
                    formatting.put("+ " + node.get("fieldName"), Objects.toString(node.get("value2")));
                }
                case "UNCHANGED" -> formatting.put(Objects.toString(node.get("fieldName")),
                        Objects.toString(node.get("value1")));
                default -> { }
            }
        }
        return new ObjectMapper().writeValueAsString(formatting);
    }
}
