package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;

public class Json {

    public static String format(LinkedHashMap<String, String> diff) throws JsonProcessingException {
        LinkedHashMap<String, String> formatting = new LinkedHashMap<>();
        diff.forEach(
                (key, value) -> {
                    switch (key.substring(0, 2)) {
                        case "++", " +" -> formatting.put("+ " + key.substring(2), value);
                        case "--", " -" -> formatting.put("- " + key.substring(2), value);
                        default -> formatting.put(key, value);
                    }
                }
        );
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(formatting);
    }

}
