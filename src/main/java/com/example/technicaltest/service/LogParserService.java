package com.example.technicaltest.service;

import java.io.FileNotFoundException;

public interface LogParserService {
    void parseLog(String fileName) throws FileNotFoundException;
}
