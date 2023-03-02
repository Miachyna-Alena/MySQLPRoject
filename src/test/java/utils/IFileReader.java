package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class IFileReader {
    private static final Logger LOGGER = LoggerFactory.getLogger(IFileReader.class);

    public static String readFile(File file) {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
            while (reader.ready()) {
                builder.append(reader.readLine());
            }
        } catch (IOException exception) {
            LOGGER.error(exception.getMessage());
        }
        return builder.toString();
    }
}
