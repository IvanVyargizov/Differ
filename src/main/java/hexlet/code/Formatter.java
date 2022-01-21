package hexlet.code;

import hexlet.code.formatters.Json;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.io.IOException;
import java.util.LinkedHashMap;

public class Formatter {

    public static String output(LinkedHashMap<String, String> diff, String outputFormat) throws IOException {
        switch (outputFormat) {
            case "json" -> {
                return Json.format(diff);
            }
            case "plain" -> {
                return Plain.format(diff);
            }
            default -> {
                return Stylish.format(diff);
            }
        }
    }
}
