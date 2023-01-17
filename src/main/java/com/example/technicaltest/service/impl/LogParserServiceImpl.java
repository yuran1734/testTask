package com.example.technicaltest.service.impl;

import com.example.technicaltest.service.LogParserService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LogParserServiceImpl implements LogParserService {
    private final static String DEFAULT_FILE_EXTENSION = "log";
    private final static String CARD_MASK = "************$1";
    private final static Pattern CARD_PATTERN = Pattern.compile("[0-9]{12}([0-9]{4})");

    @Override
    public void parseLog(final String fileName) throws FileNotFoundException {
        checkValidFileExtension(fileName);
        final File file = new File(fileName);
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                handleLine(line);
            }
        } catch (FileNotFoundException e) {
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleLine(final String line) {
        final Matcher matcher = CARD_PATTERN.matcher(line);
        if (matcher.find()) {
            System.out.println(matcher.replaceAll(CARD_MASK));
        } else {
            System.out.println(line);
        }
    }

    private void checkValidFileExtension(final String fileName) {
        final String extension = FilenameUtils.getExtension(fileName);
        if (!DEFAULT_FILE_EXTENSION.equalsIgnoreCase(extension)) {
            throw new RuntimeException("Invalid type of log file!");
        }
    }

}
