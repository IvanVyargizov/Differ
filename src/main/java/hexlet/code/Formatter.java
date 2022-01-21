package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import hexlet.code.formatters.Json;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.util.LinkedHashMap;

public class Formatter {

    public static String output(LinkedHashMap<String, String> diff, String outputFormat)
            throws JsonProcessingException {
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
