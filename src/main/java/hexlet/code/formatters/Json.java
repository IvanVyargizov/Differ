package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

public class Json {

    public static String compare(HashMap<String, String> file1, HashMap<String, String> file2)
            throws JsonProcessingException {
        TreeMap<String, String> mergingFile = new TreeMap<>(file1);
        file2.forEach(
                (key, value) -> mergingFile.merge(key, value, (value1, value2) -> value2)
        );
        LinkedHashMap<String, String> diff = new LinkedHashMap<>();
        mergingFile.forEach(
                (key, value) -> {
                    if (!file1.containsKey(key) && file2.containsKey(key)) {
                        diff.put("+ " + key, value);
                    } else if (file1.containsKey(key) && !file2.containsKey(key)) {
                        diff.put("- " + key, value);
                    } else if (!value.equals(file1.get(key))) {
                        diff.put("- " + key, file1.get(key));
                        diff.put("+ " + key, value);
                    } else {
                        diff.put(key, value);
                    }
                }
        );
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(diff);
    }

}
