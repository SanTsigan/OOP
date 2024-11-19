package ru.nsu.tsyganov.substringfinder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
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
        assertEquals(output.size(), 701);
    }

    @Test
    void testEmoji() {
        List<Integer> output = finder.find(filename3, "\uD83C\uDF40");
        List<Integer> expected = new ArrayList<>();
        expected.add(565);
        assertEquals(output, expected);
    }

    public static class RandomFileGenerator {
        private static final String CHARACTERS
                = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        public void generateRandomFile(String filePath, int size) {
            Random random = new Random();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (int i = 0; i < size; i++) {
                    int index = random.nextInt(CHARACTERS.length());
                    writer.write(CHARACTERS.charAt(index));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void findInGeneratedFile() throws IOException {
        RandomFileGenerator rgen = new RandomFileGenerator();
        rgen.generateRandomFile("largefile.txt", Integer.MAX_VALUE);
        File file = new File("largefile.txt");
        List<Integer> output = finder.find("largefile.txt", "abc");
        file.delete();
    }

}
