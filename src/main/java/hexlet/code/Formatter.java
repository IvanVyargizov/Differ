package hexlet.code;

import hexlet.code.formatters.Json;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Formatter {

    public static String output(List<Map<String, Object>> diff, String outputFormat) throws IOException {
        return switch (outputFormat) {
            case "json" -> Json.format(diff);
            case "plain" -> Plain.format(diff);
            case "stylish" -> Stylish.format(diff);
            default -> throw new RuntimeException("Unsupported output format: " + outputFormat);
        };
    }

}
