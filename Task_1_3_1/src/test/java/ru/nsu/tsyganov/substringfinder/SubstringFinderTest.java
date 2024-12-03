package ru.nsu.tsyganov.substringfinder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubstringFinderTest {
    private SubstringFinder finder;
    URL absPath;
    File file;
    private String filename1;
    private String filename2;
    private static String filename3;

    @BeforeEach
    void setup() throws URISyntaxException {
        finder = new SubstringFinder();
        absPath = SubstringFinderTest.class.getClassLoader().getResource("input.txt");
        assert absPath != null;
        file = Paths.get(absPath.toURI()).toFile();
        filename1 = file.getAbsolutePath();
        absPath = SubstringFinderTest.class.getClassLoader().getResource("example.txt");
        assert absPath != null;
        file = Paths.get(absPath.toURI()).toFile();
        filename2 = file.getAbsolutePath();
        absPath = SubstringFinderTest.class.getClassLoader().getResource("emojitext.txt");
        assert absPath != null;
        file = Paths.get(absPath.toURI()).toFile();
        filename3 = file.getAbsolutePath();
    }

    @Test
    void testBasic() {
        List<Integer> output = finder.find(filename1, "бра");
        List<Integer> expected = new ArrayList<>();
        expected.add(1);
        expected.add(8);
        assertEquals(output, expected);
    }

    @Test
    void testLargeText() {
        List<Integer> output = finder.find(filename2, "whale");
        assertEquals(output.size(), 682);
    }

    @Test
    void testEmoji() {
        List<Integer> output = finder.find(filename3, "\uD83C\uDF40");
        List<Integer> expected = new ArrayList<>();
        expected.add(570);
        assertEquals(output, expected);
    }

    public static class RandomFileGenerator {
        private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        private static final int BUFFER_SIZE = 1048576; // 1 MB
        private static final int STRING_LENGTH = 1024;

        public void generateRandomFile(String filePath, float size) {
            Random random = new Random();
            StringBuilder sb = new StringBuilder(STRING_LENGTH);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, StandardCharsets.UTF_8), BUFFER_SIZE)) {
                long totalChars = (long) (size * 1024 * 1024);
                long writtenChars = 0;

                while (writtenChars < totalChars) {
                    sb.setLength(0);
                    for (int i = 0; i < STRING_LENGTH; i++) {
                        int index = random.nextInt(CHARACTERS.length());
                        sb.append(CHARACTERS.charAt(index));
                    }
                    writer.write(sb.toString());
                    writtenChars += STRING_LENGTH;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void findInGeneratedFile() throws IOException {
        RandomFileGenerator rgen = new RandomFileGenerator();
        rgen.generateRandomFile("largefile.txt", 12288f);
        File file = new File("largefile.txt");
        List<Integer> output = finder.find("largefile.txt", "abc");
        file.delete();
    }

}
