package hexlet.code.formatters;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

public class Plain {

    public static LinkedList<String> compare(HashMap<String, String> file1, HashMap<String, String> file2) {
        TreeMap<String, String> mergingFile = new TreeMap<>(file1);
        file2.forEach(
                (key, value) -> mergingFile.merge(key, value, (value1, value2) -> value2)
        );
        LinkedList<String> diff = new LinkedList<>();
        mergingFile.forEach(
                (key, value) -> {
                    if (!file1.containsKey(key) && file2.containsKey(key)) {
                        diff.addLast("Property '" + key + "' was added with value: " + convert(value));
                    } else if (file1.containsKey(key) && !file2.containsKey(key)) {
                        diff.addLast("Property '" + key + "' was removed");
                    } else if (!value.equals(file1.get(key))) {
                        diff.addLast("Property '" + key + "' was updated. From " + convert(file1.get(key)) + " to "
                                + convert(value));
                    }
                }
        );
        return diff;
    }

    private static String convert(String element) {
        if ((element.startsWith("[") && element.endsWith("]"))
                || (element.startsWith("{") && element.endsWith("}"))) {
            return "[complex value]";
        } else if (element.equals("true") || element.equals("false") || element.equals("null")
                || element.chars().allMatch(Character::isDigit)) {
            return element;
        }
        return "'" + element + "'";
    }
}
