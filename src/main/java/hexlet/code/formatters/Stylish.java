package hexlet.code.formatters;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

public class Stylish {

    public static String compare(HashMap<String, String> file1, HashMap<String, String> file2) {
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
        return "{\n" + String.join("\n", diff) + "\n}";
    }
}
