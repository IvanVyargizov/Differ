package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Parser {

    public static Map<String, String> parse(String content, String fileFormat) throws IOException {
        switch (fileFormat) {
            case "json" -> {
                ObjectMapper mapper = new ObjectMapper();
                return convert(mapper.readValue(content, new TypeReference<>() { }));
            }
            case "yaml" -> {
                ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                mapper.findAndRegisterModules();
                return convert(mapper.readValue(content, new TypeReference<>() { }));
            }
            default -> throw new IOException();
        }
    }

    private static Map<String, String> convert(Map<String, Object> content) {
        Map<String, String> convertingContentValue = new HashMap<>();
        content.forEach(
                (key, value) -> {
                    String strValue = (value == null) ? "null" : value.toString();
                    convertingContentValue.put(key, strValue);
                }
        );
        return convertingContentValue;
    }

}
