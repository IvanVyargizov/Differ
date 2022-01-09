package hexlet.code;

import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.util.HashMap;

public class Formatter {

    public static String outputFormat(HashMap<String, String> file1, HashMap<String, String> file2, String formatName) {
        return formatName.equals("plain")
                ? String.join("\n", Plain.compare(file1, file2))
                : "{\n" + String.join("\n", Stylish.compare(file1, file2)) + "\n}";
    }
}
