package hexlet.code.formatters;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Stylish {

    public static String format(List<Map<String, Object>> diff) {
        LinkedList<String> formatting = new LinkedList<>();
        diff.forEach(
                (dict) -> {
                    switch (dict.get("status").toString()) {
                        case "ADDED" -> formatting.add("  + " + dict.get("fieldName") + ": " + dict.get("value2"));
                        case "REMOVED" -> formatting.add("  - " + dict.get("fieldName") + ": " + dict.get("value1"));
                        case "CHANGED" -> {
                            formatting.add("  - " + dict.get("fieldName") + ": " + dict.get("value1"));
                            formatting.add("  + " + dict.get("fieldName") + ": " + dict.get("value2"));
                        }
                        case "UNCHANGED" -> formatting.add("    " + dict.get("fieldName") + ": " + dict.get("value1"));
                        default -> { }
                    }
                }
        );
        return "{\n" + String.join("\n", formatting) + "\n}";
    }

}
