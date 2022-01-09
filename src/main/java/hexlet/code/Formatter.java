package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import hexlet.code.formatters.Json;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.util.HashMap;

public class Formatter {

    public static String outputFormat(HashMap<String, String> file1, HashMap<String, String> file2, String formatName)
            throws JsonProcessingException {
        if (formatName.equals("plain")) {
            return Plain.compare(file1, file2);
        } else if (formatName.equals("json")) {
            return Json.compare(file1, file2);
        } else {
            return Stylish.compare(file1, file2);
        }
    }
    public static String outputFormat(HashMap<String, String> file1, HashMap<String, String> file2) {
        return Stylish.compare(file1, file2);
    }

}
