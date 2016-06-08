package sample.data.utils;

import sample.data.db_store.transaction_strategy.PharmStrategy;
import sample.domain.models.Record;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class SqlRecordMapper {

    private static final GregorianCalendar CALENDAR = new GregorianCalendar();

    public static Record mapRecord(ResultSet result) throws Exception {
        return new Record(result.getString(PharmStrategy.COLUMN_ID),
                result.getString(PharmStrategy.COLUMN_TITLE),
                result.getString(PharmStrategy.COLUMN_PROVIDER),
                result.getString(PharmStrategy.COLUMN_CERTIFICATE),
                result.getString(PharmStrategy.COLUMN_DATE),
                result.getString(PharmStrategy.COLUMN_DESCRIPTION),
                parseDate(result.getLong(PharmStrategy.COLUMN_UPLOAD_TIME)));
    }

    public static String mapRecord(Record record) {
        return "'"+record.getId()+"', '"+record.getTitle()+"', '"+record.getProvider()+"', '"+record.getCertificate()+"', '"+record.getDate()+"', '"+record.getDescription()+"', "+System.currentTimeMillis();
    }

    private static String parseDate(Long dateLong) {
        CALENDAR.setTimeInMillis(dateLong);
        String month = (CALENDAR.get(Calendar.MONTH)+1)+"";
        if (month.length() == 1)
            month = "0"+month;
        return (CALENDAR.get(Calendar.DATE)+1)+"."+month+"."+CALENDAR.get(Calendar.YEAR);
    }
}
