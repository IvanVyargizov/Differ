package hexlet.code;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class AppTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();
    private static final String FILE_1 = "file1.json";
    private static final String FILE_2 = "file2.json";
    private static final String FILE_3 = "file3.json";
    private static final String FILE_4 = "file4.json";
    private static final String FILE_5 = "/file5.json";
    private final String filePath1 = Objects.requireNonNull(
            this.getClass().getClassLoader().getResource(FILE_1)).getPath();
    private final String filePath2 = Objects.requireNonNull(
            this.getClass().getClassLoader().getResource(FILE_2)).getPath();
    private final String filePath3 = Objects.requireNonNull(
            this.getClass().getClassLoader().getResource(FILE_3)).getPath();
    private final String filePath4 = Objects.requireNonNull(
            this.getClass().getClassLoader().getResource(FILE_4)).getPath();

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
        App.main(new String[] {filePath1, filePath2});
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
        App.main(new String[] {filePath2, filePath2});
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
        App.main(new String[] {filePath1, filePath3});
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
        App.main(new String[] {filePath2, filePath4});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    void test5() {
        String expected = """
                {
                  + host: hexlet.io
                  + timeout: 20
                  + verbose: true
                }""";
        App.main(new String[] {filePath4, filePath2});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    void test6()  {
        String expected = "Incorrect path to second file. No such file or directory";
        App.main(new String[] {filePath1, FILE_5});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

}
