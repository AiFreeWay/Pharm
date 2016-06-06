package sample.data.db_store;


import sample.data.db_store.transaction_strategy.PharmStrategy;
import sample.data.db_store.transaction_strategy.Strategy;
import sample.data.utils.DbStrategyesFactory;
import sample.domain.models.Record;

import java.sql.*;

public class DbController {

    private Statement mStatement;
    private DbStrategyesFactory mStrategyesFactory;

    public DbController() {
        initConnection();
        if (!checkExistsTable())
            createPharmTable();
        mStrategyesFactory = new DbStrategyesFactory(mStatement);
    }

    private void initConnection() {
        String url = "jdbc:postgresql://127.0.0.1:5432/pharm";
        String name = "postgres";
        String password = "12345678";

        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, name, password);
            mStatement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Strategy getStrategy(DbStrategyesFactory.TransactionStrategyes strategyType) {
        return mStrategyesFactory.getStrategy(strategyType);
    };

    private boolean checkExistsTable() {
        boolean isExists = false;
        try {
            ResultSet result = mStatement.executeQuery("SELECT EXISTS (SELECT * FROM information_schema.tables WHERE table_name = '"+ PharmStrategy.TABLE_NAME+"');");
            result.next();
            isExists = result.getBoolean("exists");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isExists;
    }

    private void createPharmTable() {
        try {
            mStatement.execute("CREATE TABLE "+PharmStrategy.TABLE_NAME+" (" +
                    PharmStrategy.COLUMN_ID+" text PRIMARY KEY," +
                    PharmStrategy.COLUMN_TITLE+" text, " +
                    PharmStrategy.COLUMN_PROVIDER+" text, " +
                    PharmStrategy.COLUMN_CERTIFICATE+" text, " +
                    PharmStrategy.COLUMN_DATE+" text, " +
                    PharmStrategy.COLUMN_DESCRIPTION+" text, " +
                    PharmStrategy.COLUMN_UPLOAD_TIME+" bigint);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
