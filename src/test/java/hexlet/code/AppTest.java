package hexlet.code;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Collectors;

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

    private static final String FILE = "file.docx";
    private final String filePath5 = Objects.requireNonNull(
            this.getClass().getClassLoader().getResource(FILE)).getPath();

    private final String stylishFormat = "stylish";
    private final String plainFormat = "plain";
    private final String jsonFormat = "json";

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(output));
    }

    @Test
    @DisplayName("Test1. 'json' file comparison test. 'stylish' format default")
    void test1() throws IOException {
        String expectedPath = Objects.requireNonNull(
                this.getClass().getClassLoader().getResource("Test1_Test2_expected")).getPath();
        String expected = Files.lines(Path.of(expectedPath)).collect(Collectors.joining("\n")).trim();
        App.main(new String[] {filePathJson1, filePathJson2});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test2. 'yaml' file comparison test. 'stylish' format default")
    void test2() throws IOException {
        String expectedPath = Objects.requireNonNull(
                this.getClass().getClassLoader().getResource("Test1_Test2_expected")).getPath();
        String expected = Files.lines(Path.of(expectedPath)).collect(Collectors.joining("\n")).trim();
        App.main(new String[] {filePathYaml1, filePathYaml2});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test 3. 'json' file comparison test. 'plain' format explicitly indicated")
    void test3() throws IOException {
        String expectedPath = Objects.requireNonNull(
                this.getClass().getClassLoader().getResource("Test3_Test4_expected")).getPath();
        String expected = Files.lines(Path.of(expectedPath)).collect(Collectors.joining("\n")).trim();
        App.main(new String[] {filePathJson1, filePathJson2, "-f", plainFormat});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test 4. 'yaml' file comparison test. 'plain' format explicitly indicated")
    void test4() throws IOException {
        String expectedPath = Objects.requireNonNull(
                this.getClass().getClassLoader().getResource("Test3_Test4_expected")).getPath();
        String expected = Files.lines(Path.of(expectedPath)).collect(Collectors.joining("\n")).trim();
        App.main(new String[] {filePathYaml1, filePathYaml2, "-f", plainFormat});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test 5. 'json' file comparison test. 'stylish' format explicitly indicated")
    void test5() throws IOException {
        String expectedPath = Objects.requireNonNull(
                this.getClass().getClassLoader().getResource("Test5_expected")).getPath();
        String expected = Files.lines(Path.of(expectedPath)).collect(Collectors.joining("\n")).trim();
        App.main(new String[] {filePathJson3, filePathJson3, "-f", stylishFormat});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test 6. 'yaml' file comparison test. 'stylish' format explicitly indicated")
    void test6() throws IOException {
        String expectedPath = Objects.requireNonNull(
                this.getClass().getClassLoader().getResource("Test6_expected")).getPath();
        String expected = Files.lines(Path.of(expectedPath)).collect(Collectors.joining("\n")).trim();
        App.main(new String[] {filePathYaml3, filePathYaml4, "-f", stylishFormat});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test 7. 'yaml' file comparison test. 'plain' format explicitly indicated")
    void test7() throws IOException {
        String expectedPath = Objects.requireNonNull(
                this.getClass().getClassLoader().getResource("Test7_expected")).getPath();
        String expected = Files.lines(Path.of(expectedPath)).collect(Collectors.joining("\n")).trim();
        App.main(new String[] {filePathYaml3, filePathYaml4, "-f", plainFormat});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test 8. 'json' file comparison test. 'stylish' format default")
    void test8() throws IOException {
        String expectedPath = Objects.requireNonNull(
                this.getClass().getClassLoader().getResource("Test8_expected")).getPath();
        String expected = Files.lines(Path.of(expectedPath)).collect(Collectors.joining("\n")).trim();
        App.main(new String[] {filePathJson4, filePathJson3});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test 9. 'json' file comparison test. 'plain' format explicitly indicated")
    void test9() throws IOException {
        String expectedPath = Objects.requireNonNull(
                this.getClass().getClassLoader().getResource("Test9_expected")).getPath();
        String expected = Files.lines(Path.of(expectedPath)).collect(Collectors.joining("\n")).trim();
        App.main(new String[] {filePathJson4, filePathJson3, "-f", plainFormat});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test 10. 'json' file comparison test. 'json' format explicitly indicated")
    void test10() throws IOException {
        String expectedPath = Objects.requireNonNull(
                this.getClass().getClassLoader().getResource("Test10_expected")).getPath();
        String expected = Files.readString(Path.of(expectedPath)).trim();
        App.main(new String[] {filePathJson1, filePathJson2, "-f", jsonFormat});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test 11. 'yaml' file comparison test. 'json' format explicitly indicated")
    void test11() {
        String expected = """
                {"- follow":"true","- host":"hexlet.io.ru","- proxy":"123.234.53.11","- timeout":"10"}""";
        App.main(new String[] {filePathYaml3, filePathYaml4, "-f", jsonFormat});
        assertThat(output.toString().trim()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Test 12. File comparison test, if the second file does not exist")
    void test12()  {
        Assertions.assertThrows(IOException.class, () -> Differ.generate(filePathJson1, FILE_5_JSON));
    }

    @Test
    @DisplayName("Test 13. File comparison test, if the first file has unsupported format")
    void test13()  {
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class,
                () -> Differ.generate(filePath5, filePathJson1));
        Assertions.assertEquals("Unsupported file format: .docx", thrown.getMessage());
    }

    @Test
    @DisplayName("Test 14. File comparison test, if the output format unsupported")
    void test14()  {
        RuntimeException thrown = Assertions.assertThrows(RuntimeException.class,
                () -> Differ.generate(filePathYaml1, filePathYaml2, "doc"));
        Assertions.assertEquals("Unsupported output format: doc", thrown.getMessage());
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

}
