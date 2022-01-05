package hexlet.code;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

public class Differ {

    public static String generate(String path1, String path2) {
        HashMap<String, String> file1;
        try {
            file1 = Parser.read(path1);
        } catch (IOException ioe) {
            return "Incorrect path to first file. No such file or path leads to multiple files with the same pathname";
        }

        HashMap<String, String> file2;
        try {
            file2 = Parser.read(path2);
        } catch (IOException ioe) {
            return "Incorrect path to second file. No such file or path leads to multiple files with the same pathname";
        }
        return "{\n" + String.join("\n", compare(file1, file2)) + "\n}";
    }

    private static LinkedList<String> compare(HashMap<String, String> file1, HashMap<String, String> file2) {
        TreeMap<String, String> mergingFile = new TreeMap<>(file1);
        file2.forEach(
                (key, value) -> mergingFile.merge(key, value, (value1, value2) -> value2)
        );
        LinkedList<String> diff = new LinkedList<>();
        mergingFile.forEach(
                (key, value) -> {
                    if (!file1.containsKey(key) && file2.containsKey(key)) {
                        diff.addLast("  + " + key + ": " + value);
                    } else if (file1.containsKey(key) && !file2.containsKey(key)) {
                        diff.addLast("  - " + key + ": " + value);
                    } else if (!value.equals(file1.get(key))) {
                        diff.addLast("  - " + key + ": " + file1.get(key));
                        diff.addLast("  + " + key + ": " + value);
                    } else {
                        diff.addLast("    " + key + ": " + value);
                    }
                }
        );
        return diff;
    }

}
