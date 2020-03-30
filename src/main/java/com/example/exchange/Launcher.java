package com.example.exchange;

import com.example.exchange.utils.FileReader;

import java.io.IOException;
import java.util.List;

public class Launcher {
    private static final String FILE_IN = "src/main/resources/in.txt";
    private static final String FILE_OUT = "src/main/resources/out.txt";
    private static final String FILE_CANNOT_READ_MESSAGE = "Something wrong with this file";
    private FileReader fileReader = new FileReader();

    public void run() {
        try {
            List<String> fromIn = fileReader.read(FILE_IN);
            fileReader.write(FILE_OUT,fromIn);
        } catch (IOException e) {
            printMessage(FILE_CANNOT_READ_MESSAGE);
        }
    }

    private void printMessage(String message) {
        System.out.println(message);
    }
}
