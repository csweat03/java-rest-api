package me.christian.utility;

import me.christian.interfaces.Action;

import java.io.*;
import java.util.Arrays;

public class FileUtility {

    public static BufferedReader instantiateNewReader(String fileName) {
        try {
            return new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException fileNotFoundException) {
            throw new RuntimeException(fileNotFoundException);
        }
    }

    public static BufferedWriter instantiateNewWriter(String fileName) {
        try {
            return new BufferedWriter(new FileWriter(fileName, true));
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    public static void readAllContent(BufferedReader reader, Action callback) {
        reader.lines().forEach(callback::handle);
    }

    public static void writeAllContent(BufferedWriter writer, String... lines) {
        Arrays.asList(lines).forEach(line -> {
            try {
                writer.write(line);
                writer.flush();
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        });
    }

    public static void appendAllContent(BufferedWriter writer, String... lines) {
        Arrays.asList(lines).forEach(line -> {
            try {
                writer.append(line);
                writer.flush();
            } catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        });
    }

}
