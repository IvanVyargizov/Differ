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
                    chars1: [a, b, c]
                  - chars2: [d, e, f]
                  + chars2: false
                  - checked: false
                  + checked: true
                  - default: null
                  + default: [value1, value2]
                  - id: 45
                  + id: null
                  - key1: value1
                  + key2: value2
                    numbers1: [1, 2, 3, 4]
                  - numbers2: [2, 3, 4, 5]
                  + numbers2: [22, 33, 44, 55]
                  - numbers3: [3, 4, 5]
                  + numbers4: [4, 5, 6]
                  + obj1: {nestedKey=value, isNested=true}
                  - setting1: Some value
                  + setting1: Another value
                  - setting2: 200
                  + setting2: 300
                  - setting3: true
                  + setting3: none
                }""";
        App.main(new String[] {filePathJson1, filePathJson2});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    void testYaml1() {
        String expected = """
                {
                    chars1: [a, b, c]
                  - chars2: [d, e, f]
                  + chars2: false
                  - checked: false
                  + checked: true
                  - default: null
                  + default: [value1, value2]
                  - id: 45
                  + id: null
                  - key1: value1
                  + key2: value2
                    numbers1: [1, 2, 3, 4]
                  - numbers2: [2, 3, 4, 5]
                  + numbers2: [22, 33, 44, 55]
                  - numbers3: [3, 4, 5]
                  + numbers4: [4, 5, 6]
                  + obj1: {nestedKey=value, isNested=true}
                  - setting1: Some value
                  + setting1: Another value
                  - setting2: 200
                  + setting2: 300
                  - setting3: true
                  + setting3: none
                }""";
        App.main(new String[] {filePathYaml1, filePathYaml2});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    void testJson2() {
        String expected = """
                {
                    follow: true
                    host: hexlet.io.ru
                    proxy: 123.234.53.11
                    timeout: 10
                }""";
        App.main(new String[] {filePathJson3, filePathJson3});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    void testYaml2() {
        String expected = """
                {
                    follow: true
                    host: hexlet.io.ru
                    proxy: 123.234.53.11
                    timeout: 10
                }""";
        App.main(new String[] {filePathYaml3, filePathYaml3});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    void testJson3() {
        String expected = """
                {
                  - follow: true
                  - host: hexlet.io.ru
                  - proxy: 123.234.53.11
                  - timeout: 10
                }""";
        App.main(new String[] {filePathJson3, filePathJson4});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    void testYaml3() {
        String expected = """
                {
                  - follow: true
                  - host: hexlet.io.ru
                  - proxy: 123.234.53.11
                  - timeout: 10
                }""";
        App.main(new String[] {filePathYaml3, filePathYaml4});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    void testJson4() {
        String expected = """
                {
                  + follow: true
                  + host: hexlet.io.ru
                  + proxy: 123.234.53.11
                  + timeout: 10
                }""";
        App.main(new String[] {filePathJson4, filePathJson3});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    void testYaml4() {
        String expected = """
                {
                  + follow: true
                  + host: hexlet.io.ru
                  + proxy: 123.234.53.11
                  + timeout: 10
                }""";
        App.main(new String[] {filePathYaml4, filePathYaml3});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    void test5()  {
        String expected = "Incorrect path to second file. "
                + "No such file or path leads to multiple files with the same pathname";
        App.main(new String[] {filePathJson1, FILE_5_JSON});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

}
