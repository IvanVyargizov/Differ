package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.util.Map;

public class Parser {

    public static Map<String, Object> parse(String content, String fileFormat) throws IOException {
        return setObjectMapper(fileFormat).readValue(content, new TypeReference<>() { });
    }

    private static ObjectMapper setObjectMapper(String fileFormat) {
        return switch (fileFormat) {
            case ".json" -> new ObjectMapper();
            case ".yml" -> new ObjectMapper(new YAMLFactory()).findAndRegisterModules();
            default -> throw new RuntimeException("Unsupported file format: " + fileFormat);
        };
    }

}
