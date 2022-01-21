package hexlet.code.formatters;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Stylish {

    public static String format(LinkedHashMap<String, String> diff) {
        LinkedList<String> formatting = new LinkedList<>();
        diff.forEach(
                (key, value) -> {
                    switch (key.substring(0, 2)) {
                        case "++", " +" -> formatting.add("  + " + key.substring(2) + ": " + value);
                        case "--", " -" -> formatting.add("  - " + key.substring(2) + ": " + value);
                        default -> formatting.add("    " + key + ": " + value);
                    }
                }
        );
        return "{\n" + String.join("\n", formatting) + "\n}";
    }

}
