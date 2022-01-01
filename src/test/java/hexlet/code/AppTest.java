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
    private static final String FILE_1_JSON = "file1.json";
    private static final String FILE_2_JSON = "file2.json";
    private static final String FILE_3_JSON = "file3.json";
    private static final String FILE_4_JSON = "file4.json";
    private static final String FILE_5_JSON = "/file5.json";
    private final String filePathJson1 = Objects.requireNonNull(
            this.getClass().getClassLoader().getResource(FILE_1_JSON)).getPath();
    private final String filePathJson2 = Objects.requireNonNull(
            this.getClass().getClassLoader().getResource(FILE_2_JSON)).getPath();
    private final String filePathJson3 = Objects.requireNonNull(
            this.getClass().getClassLoader().getResource(FILE_3_JSON)).getPath();
    private final String filePathJson4 = Objects.requireNonNull(
            this.getClass().getClassLoader().getResource(FILE_4_JSON)).getPath();

    private static final String FILE_1_YAML = "file1.yml";
    private static final String FILE_2_YAML = "file2.yml";
    private static final String FILE_3_YAML = "file3.yml";
    private static final String FILE_4_YAML = "file4.yml";
    private final String filePathYaml1 = Objects.requireNonNull(
            this.getClass().getClassLoader().getResource(FILE_1_YAML)).getPath();
    private final String filePathYaml2 = Objects.requireNonNull(
            this.getClass().getClassLoader().getResource(FILE_2_YAML)).getPath();
    private final String filePathYaml3 = Objects.requireNonNull(
            this.getClass().getClassLoader().getResource(FILE_3_YAML)).getPath();
    private final String filePathYaml4 = Objects.requireNonNull(
            this.getClass().getClassLoader().getResource(FILE_4_YAML)).getPath();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(output));
    }

    @Test
    void testJson1() {
        String expected = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";
        App.main(new String[] {filePathJson1, filePathJson2});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    void testYaml1() {
        String expected = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";
        App.main(new String[] {filePathYaml1, filePathYaml2});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    void testJson2() {
        String expected = """
                {
                    host: hexlet.io
                    timeout: 20
                    verbose: true
                }""";
        App.main(new String[] {filePathJson2, filePathJson2});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    void testYaml2() {
        String expected = """
                {
                    host: hexlet.io
                    timeout: 20
                    verbose: true
                }""";
        App.main(new String[] {filePathYaml2, filePathYaml2});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    void testJson3() {
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
        App.main(new String[] {filePathJson1, filePathJson3});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    void testYaml3() {
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
        App.main(new String[] {filePathYaml1, filePathYaml3});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    void testJson4() {
        String expected = """
                {
                  - host: hexlet.io
                  - timeout: 20
                  - verbose: true
                }""";
        App.main(new String[] {filePathJson2, filePathJson4});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    void testYaml4() {
        String expected = """
                {
                  - host: hexlet.io
                  - timeout: 20
                  - verbose: true
                }""";
        App.main(new String[] {filePathYaml2, filePathYaml4});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    void testJson5() {
        String expected = """
                {
                  + host: hexlet.io
                  + timeout: 20
                  + verbose: true
                }""";
        App.main(new String[] {filePathJson4, filePathJson2});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    void testYaml5() {
        String expected = """
                {
                  + host: hexlet.io
                  + timeout: 20
                  + verbose: true
                }""";
        App.main(new String[] {filePathYaml4, filePathYaml2});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    void test6()  {
        String expected = "Incorrect path to second file. No such file or directory";
        App.main(new String[] {filePathJson1, FILE_5_JSON});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

}
