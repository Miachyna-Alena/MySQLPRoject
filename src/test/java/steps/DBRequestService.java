package steps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ConfigProvider;
import utils.DBUtils;
import utils.TestDataUtils;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DBRequestService {
    private static PreparedStatement preparedStatement;
    private static final Logger LOGGER = LoggerFactory.getLogger(DBRequestService.class);
    private static final String SELECT_MIN_WORKING_TESTS_TIME = ConfigProvider.readConfig("requests-data.json").getString("SELECT_MIN_WORKING_TESTS_TIME");
    private static final String SELECT_UNIQUE_TESTS_COUNT_BY_PROJECT = ConfigProvider.readConfig("requests-data.json").getString("SELECT_UNIQUE_TESTS_COUNT_BY_PROJECT");
    private static final String SELECT_TESTS_EXECUTED_AFTER_SPECIFIC_DATE = ConfigProvider.readConfig("requests-data.json").getString("SELECT_TESTS_EXECUTED_AFTER_SPECIFIC_DATE");
    private static final String SELECT_TESTS_EXECUTED_WITH_SPECIFIC_BROWSERS = ConfigProvider.readConfig("requests-data.json").getString("SELECT_TESTS_EXECUTED_WITH_SPECIFIC_BROWSERS");

    public static PreparedStatement getSelectMinWorkingTestsTime() {
        return DBUtils.getPreparedStatement(SELECT_MIN_WORKING_TESTS_TIME);
    }

    public static PreparedStatement getSelectUniqueTestsCountByProject() {
        return DBUtils.getPreparedStatement(SELECT_UNIQUE_TESTS_COUNT_BY_PROJECT);
    }

    public static PreparedStatement getSelectTestsExecutedAfterSpecificDate(String date) {
        try {
            preparedStatement = DBUtils.getPreparedStatement(SELECT_TESTS_EXECUTED_AFTER_SPECIFIC_DATE);
            assert preparedStatement != null;
            preparedStatement.setString(1, date);
            return preparedStatement;
        } catch (SQLException exception) {
            LOGGER.info(exception.getMessage());
            return null;
        }
    }

    public static PreparedStatement getSelectTestsExecutedWithSpecificBrowsers() {

        String[] browsers = TestDataUtils.getBrowsers();

        StringBuilder request = new StringBuilder();
        boolean isFirstBrowser = true;
        for (String browser : browsers) {
            if (!isFirstBrowser) {
                request.append("\nunion\n");
            }
            isFirstBrowser = false;
            request.append("\n")
                    .append(SELECT_TESTS_EXECUTED_WITH_SPECIFIC_BROWSERS)
                    .append(browser);
        }
        return DBUtils.getPreparedStatement(request.toString());
    }
}
