package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Json {

    public static String format(List<Map<String, Object>> diff) throws IOException {
        Map<String, String> formatting = new LinkedHashMap<>();
        diff.forEach(
                (dict) -> {
                    switch (dict.get("status").toString()) {
                        case "ADDED" -> formatting.put("+ " + dict.get("fieldName"),
                                Objects.toString(dict.get("value2")));
                        case "REMOVED" -> formatting.put("- " + dict.get("fieldName"),
                                Objects.toString(dict.get("value1")));
                        case "CHANGED" -> {
                            formatting.put("- " + dict.get("fieldName"), Objects.toString(dict.get("value1")));
                            formatting.put("+ " + dict.get("fieldName"), Objects.toString(dict.get("value2")));
                        }
                        case "UNCHANGED" -> formatting.put(Objects.toString(dict.get("fieldName")),
                                Objects.toString(dict.get("value1")));
                        default -> { }
                    }
                }
        );
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(formatting);
    }

}
