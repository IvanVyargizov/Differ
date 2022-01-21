package hexlet.code.formatters;

import java.util.LinkedHashMap;
import java.util.LinkedList;

public class Plain {

    public static String format(LinkedHashMap<String, String> diff) {
        LinkedList<String> formatting = new LinkedList<>();
        diff.forEach(
                (key, value) -> {
                    switch (key.substring(0, 2)) {
                        case "++" -> formatting.add("Property '" + key.substring(2) + "' was added with value: "
                                + convert(value));
                        case "--" -> formatting.add("Property '" + key.substring(2) + "' was removed");
                        case " -" -> formatting.add("Property '" + key.substring(2) + "' was updated. From "
                                + convert(value) + " to ");
                        case " +" -> {
                            String update = formatting.getLast() + convert(value);
                            formatting.removeLast();
                            formatting.add(update);
                        }
                        default -> { }
                    }
                }
        );
        return String.join("\n", formatting);
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
