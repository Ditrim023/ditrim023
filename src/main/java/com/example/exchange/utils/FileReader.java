package com.example.exchange.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nikita Krutoguz
 */
public class FileReader {
    public List<String> read(String pathName) throws IOException {
        return Files.lines(Paths.get(pathName)).collect(Collectors.toList());
    }

    public void write(String pathName, List<String> fileData) throws IOException {
        FileWriter writer = new FileWriter(pathName);
        for (String str : fileData) {
            writer.write(str);
        }
        writer.close();
    }
}
