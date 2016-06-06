package sample.data.utils;

import sample.data.db_store.transaction_strategy.PharmStrategy;
import sample.domain.models.Record;

import java.sql.ResultSet;

public class SqlRecordMapper {

    public static Record mapRecord(ResultSet result) throws Exception {
        return new Record(result.getString(PharmStrategy.COLUMN_ID),
                result.getString(PharmStrategy.COLUMN_TITLE),
                result.getString(PharmStrategy.COLUMN_PROVIDER),
                result.getString(PharmStrategy.COLUMN_CERTIFICATE),
                result.getString(PharmStrategy.COLUMN_DATE),
                result.getString(PharmStrategy.COLUMN_DESCRIPTION));
    }

    public static String mapRecord(Record record) {
        return "'"+record.getId()+"', '"+record.getTitle()+"', '"+record.getProvider()+"', '"+record.getCertificate()+"', '"+record.getDate()+"', '"+record.getDescription()+"', "+System.nanoTime();
    }
}
