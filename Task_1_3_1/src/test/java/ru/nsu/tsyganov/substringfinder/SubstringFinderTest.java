package ru.nsu.tsyganov.substringfinder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubstringFinderTest {
    private SubstringFinder finder;
    URL absPath;
    File file;
    private String filename1;
    private String filename2;

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
        assertEquals(output.size(), 1362);
    }
}
