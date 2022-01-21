package hexlet.code;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Differ {

    public static String generate(String path1, String path2, String outputFormat) {
        Map<String, String> fileContent1;
        try {
            fileContent1 = Parser.parse(read(path1), getFileFormat(path1));
        } catch (IOException ioe) {
            return "Error. Please check the passed path to first file and format of first file "
                    + "(the file format should be 'json' or 'yml')";
        }
        Map<String, String> fileContent2;
        try {
            fileContent2 = Parser.parse(read(path2), getFileFormat(path2));
        } catch (IOException ioe) {
            return "Error. Please check the passed path to second file and format of second file "
                    + "(the file format should be 'json' or 'yml')";
        }
        TreeMap<String, String> mergingContent = new TreeMap<>(fileContent1);
        fileContent2.forEach(
                (key, value) -> mergingContent.merge(key, value, (value1, value2) -> value2)
        );
        LinkedHashMap<String, String> diff = new LinkedHashMap<>();
        mergingContent.forEach(
                (key, value) -> {
                    if (!fileContent1.containsKey(key) && fileContent2.containsKey(key)) {
                        diff.put("++" + key, value);
                    } else if (fileContent1.containsKey(key) && !fileContent2.containsKey(key)) {
                        diff.put("--" + key, value);
                    } else if (!value.equals(fileContent1.get(key))) {
                        diff.put(" -" + key, fileContent1.get(key));
                        diff.put(" +" + key, value);
                    } else {
                        diff.put(key, value);
                    }
                }
        );
        try {
            return Formatter.output(diff, outputFormat);
        } catch (IOException ioe) {
            return ioe.getMessage();
        }

    }

    public static String generate(String path1, String path2) {
        final String defaultFormat = "stylish";
        return generate(path1, path2, defaultFormat);
    }

    private static String read(String path) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(castAbsolutePath(path)));
        String line;
        StringBuilder fileContent = new StringBuilder();
        while ((line = br.readLine()) != null) {
            fileContent.append(line).append("\n");
        }
        return fileContent.toString().trim();
    }

    private static String getFileFormat(String path) throws IOException {
        if (path.endsWith(".json")) {
            return "json";
        } else if (path.endsWith(".yml")) {
            return "yaml";
        } else {
            throw new IOException();
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
