package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.Objects;

public class DBUtils {
    private static Connection connection;
    private static final Logger LOGGER = LoggerFactory.getLogger(DBUtils.class);
    private static ResultSet resultSet;
    private static final String DB_URL = ConfigProvider.readConfig("config-data.json").getString("DB_URL");
    private static final String USER_NAME = ConfigProvider.readConfig("credentials-data.json").getString("USER_NAME");
    private static final String PASSWORD = ConfigProvider.readConfig("credentials-data.json").getString("PASSWORD");

    public static String[][] getResultArrayByQuery(PreparedStatement preparedStatement) {
        String[][] responseResultArray;
        try {
            resultSet = Objects.requireNonNull(preparedStatement).executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            resultSet.last();
            int rowCount = resultSet.getRow() + 1;
            responseResultArray = new String[rowCount][columnCount];
            resultSet.beforeFirst();

            for (int i = 0; i < columnCount; i++) {
                responseResultArray[0][i] = metaData.getColumnLabel(i + 1);
            }

            for (int i = 1; i < rowCount; i++) {
                resultSet.next();
                for (int j = 0; j < columnCount; j++) {
                    responseResultArray[i][j] = String.valueOf(resultSet.getObject(j + 1));
                }
            }

        } catch (SQLException exception) {
            LOGGER.error(exception.getMessage());
            return null;

        }
        closeAll(resultSet, preparedStatement, connection);
        return responseResultArray;
    }

    public static PreparedStatement getPreparedStatement(String query) {
        if (DB_URL == null) {
            LOGGER.info("Database URL is NULL.");
            return null;
        }
        try {
            connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            return connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        } catch (SQLException exception) {
            LOGGER.error(exception.getMessage());
            return null;
        }
    }

    private static void closeAll(ResultSet resultSet, Statement statement, Connection connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException exception) {
                LOGGER.error(exception.getMessage());
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException exception) {
                LOGGER.error(exception.getMessage());
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException exception) {
                LOGGER.error(exception.getMessage());
            }
        }
    }
}
