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

    public static String generate(String path1, String path2) {
        HashMap<String, String> file1;
        try {
            file1 = read(path1);
        } catch (IOException ioe) {
            return "Incorrect path to first file. No such file or directory";
        }

        HashMap<String, String> file2;
        try {
            file2 = read(path2);
        } catch (IOException ioe) {
            return "Incorrect path to second file. No such file or directory";
        }

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
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(new File(castAbsolutePath(path)));
        String fileContent = jsonNode.toString();
        return objectMapper.readValue(fileContent, new TypeReference<>() { });
    }

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
