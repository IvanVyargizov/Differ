package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.HashMap;

public class Differ {

    public static String generate(String path1, String path2, String formatName) throws JsonProcessingException {
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
        return Formatter.outputFormat(file1, file2, formatName);
    }

    public static String generate(String path1, String path2) {
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
        return Formatter.outputFormat(file1, file2);
    }
}
