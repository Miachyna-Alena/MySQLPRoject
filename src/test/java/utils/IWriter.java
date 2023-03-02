package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class IWriter {
    private static final Logger LOGGER = LoggerFactory.getLogger(IWriter.class);

    public static void printResult(String[][] result) {
        int[] columnsLength = getColumnLength(result);

        StringBuilder string = new StringBuilder();
        for (String[] strings : result) {
            for (int j = 0; j < strings.length; j++) {
                char[] columnValue = getChars(strings[j], columnsLength[j]);
                string.append(columnValue);
                string.append(" ");
            }
            string.append("\n");
        }
        LOGGER.info(string.toString());
    }

    private static char[] getChars(String s, int i1) {
        char[] columnValue = new char[i1];
        char[] tmp = s.toCharArray();
        Arrays.fill(columnValue, ' ');
        System.arraycopy(tmp, 0, columnValue, 0, tmp.length);
        return columnValue;
    }

    private static int[] getColumnLength(String[][] array) {
        int[] columnsLength = new int[array[0].length];
        for (String[] strings : array) {
            for (int j = 0, k = 0; j < strings.length; j++, k++) {
                if (k == array[0].length) {
                    k = 0;
                }
                if (strings[j].length() > columnsLength[k]) {
                    columnsLength[k] = strings[j].length();
                }
            }
        }
        return columnsLength;
    }
}
