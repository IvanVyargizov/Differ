package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Parser {

    public static HashMap<String, String> read(String path) throws IOException {
        int caseFormat = 0;
        if (path.endsWith(".json")) {
            caseFormat = 1;
        } else if (path.endsWith(".yml")) {
            caseFormat = 2;
        }
        switch (caseFormat) {
            case 1 -> {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(new File(castAbsolutePath(path)));
                String fileContent = jsonNode.toString();
                return objectMapper.readValue(fileContent, new TypeReference<>() { });
            }
            case 2 -> {
                ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
                objectMapper.findAndRegisterModules();
                return objectMapper.readValue(new File(castAbsolutePath(path)), new TypeReference<>() { });
            }
            default -> {
                System.out.println("unsupported format");
                return new HashMap<>();
            }
        }
    }

    private static String castAbsolutePath(String path) throws IOException {
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
