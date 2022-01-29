package hexlet.code.formatters;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Plain {

    public static String format(List<Map<String, Object>> diff) {
        LinkedList<String> formatting = new LinkedList<>();
        diff.forEach(
                (dict) -> {
                    switch (dict.get("status").toString()) {
                        case "ADDED" -> formatting.add("Property '" + dict.get("fieldName") + "' was added with value: "
                                + convert(dict.get("value2")));
                        case "REMOVED" -> formatting.add("Property '" + dict.get("fieldName") + "' was removed");
                        case "CHANGED" -> formatting.add("Property '" + dict.get("fieldName") + "' was updated. From "
                                + convert(dict.get("value1")) + " to " + convert(dict.get("value2")));
                        default -> { }
                    }
                }
        );
        return String.join("\n", formatting);
    }


    private static String convert(Object element) {
        if (Objects.equals(element, null) || element.equals(true) || element.equals(false)
                || element.toString().chars().allMatch(Character::isDigit)) {
            return Objects.toString(element);
        }
        if ((element.toString().startsWith("[") && element.toString().endsWith("]"))
                || (element.toString().startsWith("{") && element.toString().endsWith("}"))) {
            return "[complex value]";
        }
        return "'" + element + "'";
    }
}
