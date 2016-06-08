package sample.data.db_store.transaction_strategy;


import sample.data.utils.SqlRecordMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SharedPreferenseStrategy extends Strategy {

    public static final String TABLE_NAME = "preferense";
    public static final String COLUMN_KEY = "key";
    public static final String COLUMN_VALUE = "value";

    public SharedPreferenseStrategy(Statement statement) {
        super(statement);
    }

    public String get(String key) throws Exception {
        ResultSet result = mStatement.executeQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_KEY+" = '"+key+"';");
        result.next();
        return result.getString(COLUMN_VALUE);
    }

    public void put(String key, String value) throws Exception {
        try {
            mStatement.execute("DELETE FROM "+TABLE_NAME+" WHERE "+COLUMN_KEY+" = '"+key+"';");
        } catch (SQLException e) {

        }
        mStatement.execute("INSERT INTO "+TABLE_NAME+" VALUES ('"+key+"', '"+value+"');");
    }

    public void delete(String key) throws Exception {
        mStatement.execute("DELETE FROM "+TABLE_NAME+" WHERE "+COLUMN_KEY+" = '"+key+"';");
    }
}
