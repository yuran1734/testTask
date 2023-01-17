package com.example.technicaltest;

import com.example.technicaltest.service.LogParserService;
import com.example.technicaltest.service.impl.LogParserServiceImpl;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class LogParserServiceTest {

    private final LogParserService logParserService = new LogParserServiceImpl();

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Test
    public void testLogMask() throws IOException {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
        logParserService.parseLog("src/test/resources/application.log");
        assertEquals(getExpectedOutput(), outContent.toString());
        assertTrue(errContent.toString().isEmpty());
    }

    @Test
    public void testNotExistingFile() {
        Exception exception = assertThrows(FileNotFoundException.class, () -> {
            System.setOut(new PrintStream(outContent));
            System.setErr(new PrintStream(errContent));
            logParserService.parseLog("not-existing-log.log");
            assertTrue(outContent.toString().isEmpty());
            assertTrue(errContent.toString().isEmpty());
        });
        assertEquals("not-existing-log.log (No such file or directory)", exception.getMessage());
    }

    private String getExpectedOutput() throws IOException {
        return Files.readString(Path.of("src/test/resources/expected-output.log"));
    }

}
