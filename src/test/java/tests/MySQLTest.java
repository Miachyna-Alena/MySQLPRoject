package tests;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import steps.DBRequestService;
import utils.DBUtils;
import utils.IWriter;
import utils.TestDataUtils;

import java.util.Arrays;

public class MySQLTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(MySQLTest.class);

    @Test
    public static void Step1RequestsTest() {
        LOGGER.info("Step 1. Output the minimum running time for each test (sort by projects and by tests into projects).");
        IWriter.printResult(DBUtils.getResultArrayByQuery(DBRequestService.getSelectMinWorkingTestsTime()));
    }

    @Test
    public static void Step2RequestsTest() {
        LOGGER.info("Step 2. Output all projects with the number of unique tests in the project.");
        IWriter.printResult(DBUtils.getResultArrayByQuery(DBRequestService.getSelectUniqueTestsCountByProject()));
    }

    @Test
    public static void Step3RequestsTest() {
        LOGGER.info("Step 3. Output tests for each project that has been executed after " + TestDataUtils.getDate() + "(sort by projects and by tests into projects).");
        IWriter.printResult(DBUtils.getResultArrayByQuery(DBRequestService.getSelectTestsExecutedAfterSpecificDate(TestDataUtils.getDate())));
    }

    @Test
    public static void Step4RequestsTest() {
        LOGGER.info("Step 4. Output the number of tests that had executed with: " + Arrays.toString(TestDataUtils.getBrowsers()));
        IWriter.printResult(DBUtils.getResultArrayByQuery(DBRequestService.getSelectTestsExecutedWithSpecificBrowsers()));
    }
}
