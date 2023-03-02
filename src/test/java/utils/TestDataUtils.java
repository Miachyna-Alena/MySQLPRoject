package utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class TestDataUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestDataUtils.class);
    private static final String TEST_DATA_FILE_SOURS = ConfigProvider.readConfig("config-data.json").getString("TEST_DATA_FILE_SOURCE");
    private static final String BROWSERS = "BROWSERS";
    private static final String DATE = "DATE";
    private static String testDataString;

    private static void getTestDataString() {
        if (testDataString == null) {
            File testDataFile = new File(TEST_DATA_FILE_SOURS);
            testDataString = IFileReader.readFile(testDataFile);
        }
    }

    private static Object getParam(String paramName) {
        try {
            return new JSONObject(testDataString).get(paramName);
        } catch (JSONException exception) {
            LOGGER.error(exception.getMessage());
            return null;
        }
    }

    public static String getDate() {
        getTestDataString();
        return (String) getParam(DATE);
    }

    public static String[] getBrowsers() {
        getTestDataString();
        JSONArray jsonArray = (JSONArray) getParam(BROWSERS);
        assert jsonArray != null;
        String[] result = new String[jsonArray.length()];
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                result[i] = jsonArray.getString(i);
            } catch (JSONException exception) {
                LOGGER.error(exception.getMessage());
            }
        }
        return result;
    }
}
