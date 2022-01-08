package hexlet.code;

import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.io.IOException;
import java.util.HashMap;

public class Differ {

    public static String generate(String path1, String path2, String formatName) {
        HashMap<String, String> file1;
        try {
            file1 = Parser.read(path1);
        } catch (IOException ioe) {
            return "Incorrect path to first file. No such file or path leads to multiple files with the same pathname";
        }

        HashMap<String, String> file2;
        try {
            file2 = Parser.read(path2);
        } catch (IOException ioe) {
            return "Incorrect path to second file. No such file or path leads to multiple files with the same pathname";
        }
        return formatName.equals("plain")
                ? String.join("\n", Plain.compare(file1, file2))
                : "{\n" + String.join("\n", Stylish.compare(file1, file2)) + "\n}";
    }
}
