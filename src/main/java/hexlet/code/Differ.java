package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Differ {

    public static String generate(String path1, String path2) throws IOException {
        HashMap<String, String> file1 = read(path1);
        HashMap<String, String> file2 = read(path2);
        TreeMap<String, String> mergingFile = new TreeMap<>(file1);
        file2.forEach(
                (key, value) -> mergingFile.merge(key, value, (value1, value2) -> value2)
        );
        LinkedList<String> diff = new LinkedList<>();
        mergingFile.forEach(
                (key, value) -> {
                    if (!file1.containsKey(key) && file2.containsKey(key)) {
                        diff.add("  + " + key + ": " + value);
                    } else if (file1.containsKey(key) && !file2.containsKey(key)) {
                        diff.add("  - " + key + ": " + value);
                    } else if (!value.equals(file1.get(key))) {
                        diff.add("  - " + key + ": " + file1.get(key));
                        diff.add("  + " + key + ": " + value);
                    } else {
                        diff.add("    " + key + ": " + value);
                    }
                }
        );
        return "{\n" + String.join("\n", diff) + "\n}";
    }

    private static HashMap<String, String> read(String path) throws IOException {
        String absolutePath = castAbsolutePath(path);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new File(absolutePath));
        String fileContent = jsonNode.toString();
        return objectMapper.readValue(fileContent, new TypeReference<>() { });
    }

    private static String castAbsolutePath(String path) throws IOException {
        Path checking = Paths.get(path);
        if (checking.isAbsolute()) {
            return path;
        }
        Path rootPath = Paths.get("/home");
        return Files.find(rootPath,
                Integer.MAX_VALUE,
                (p, basicFileAttributes) ->
                        p.getFileName().toString().equals(path))
                .collect(Collectors.toList()).toString()
                .replaceAll("\\[", "")
                .replaceAll("]", "");
    }

}
