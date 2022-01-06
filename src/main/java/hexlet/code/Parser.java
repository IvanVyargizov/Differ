package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
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
                ObjectMapper mapper = new ObjectMapper();
                HashMap<String, String> fileContent = new HashMap<>();
                mapper.readValue(new File(castAbsolutePath(path)), new TypeReference<HashMap<String, Object>>() { })
                        .forEach(
                        (key, value) -> {
                            if (value == null) {
                                fileContent.put(key, "null");
                            } else {
                                fileContent.put(key, value.toString());
                            }
                        }
                    );
                return fileContent;
            }
            case 2 -> {
                ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                mapper.findAndRegisterModules();
                HashMap<String, String> fileContent = new HashMap<>();
                mapper.readValue(new File(castAbsolutePath(path)), new TypeReference<HashMap<String, Object>>() { })
                        .forEach(
                                (key, value) -> {
                                    if (value == null) {
                                        fileContent.put(key, "null");
                                    } else {
                                        fileContent.put(key, value.toString());
                                    }
                                }
                    );
                return fileContent;
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
