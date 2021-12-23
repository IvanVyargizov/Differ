package hexlet.code;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private static final String FILE_1 = "file1.json";
    private static final Path FILE_PATH_1 = Paths.get(FILE_1);
    private static final String FILE_2 = "file2.json";
    private static final Path FILE_PATH_2 = Paths.get(FILE_2);
    private static final String FILE_3 = "file3.json";
    private static final Path FILE_PATH_3 = Paths.get(FILE_3);
    private static final String FILE_4 = "file4.json";
    private static final Path FILE_PATH_4 = Paths.get(FILE_4);
    private static final String FILE_5 = "file5.json";

    @BeforeAll
    static void createFile() throws IOException {
        Files.createFile(FILE_PATH_1);
        String contentFile1 = """
                {
                  "host": "hexlet.io",
                  "timeout": 50,
                  "proxy": "123.234.53.22",
                  "follow": false
                }""";
        Files.writeString(FILE_PATH_1, contentFile1);

        Files.createFile(FILE_PATH_2);
        String contentFile2 = """
                {
                  "timeout": 20,
                  "verbose": true,
                  "host": "hexlet.io"
                }""";
        Files.writeString(FILE_PATH_2, contentFile2);

        Files.createFile(FILE_PATH_3);
        String contentFile3 = """
                {
                  "timeout": 10,
                  "follow": true,
                  "host": "hexlet.io.ru",
                  "proxy": "123.234.53.11"
                }""";
        Files.writeString(FILE_PATH_3, contentFile3);

        Files.createFile(FILE_PATH_4);
        String contentFile4 = """
                {
                }""";
        Files.writeString(FILE_PATH_4, contentFile4);
    }

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(output));
    }

    @Test
    void test1() {
        String expected = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";
        App.main(new String[] {FILE_1, FILE_2});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    void test2() {
        String expected = """
                {
                    host: hexlet.io
                    timeout: 20
                    verbose: true
                }""";
        App.main(new String[] {FILE_2, FILE_2});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    void test3() {
        String expected = """
                {
                  - follow: false
                  + follow: true
                  - host: hexlet.io
                  + host: hexlet.io.ru
                  - proxy: 123.234.53.22
                  + proxy: 123.234.53.11
                  - timeout: 50
                  + timeout: 10
                }""";
        App.main(new String[] {FILE_1, FILE_3});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    void test4() {
        String expected = """
                {
                  - host: hexlet.io
                  - timeout: 20
                  - verbose: true
                }""";
        App.main(new String[] {FILE_2, FILE_4});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    void test5() {
        String expected = """
                {
                  - host: hexlet.io
                  - timeout: 20
                  - verbose: true
                }""";
        String absoluteFilePath2 = FILE_PATH_2.toAbsolutePath().toString();
        App.main(new String[] {absoluteFilePath2, FILE_4});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    void test6()  {
        FileNotFoundException exception = new FileNotFoundException();
        String expected = "Incorrect path to second file. Exception: " + exception + ":  (No such file or directory)";
        App.main(new String[] {FILE_1, FILE_5});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @AfterAll
    static void deleteFile() throws IOException {
        Files.delete(FILE_PATH_1);
        Files.delete(FILE_PATH_2);
        Files.delete(FILE_PATH_3);
        Files.delete(FILE_PATH_4);
    }

}
