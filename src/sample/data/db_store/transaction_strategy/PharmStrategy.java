package sample.data.db_store.transaction_strategy;


import sample.data.utils.SqlRecordMapper;
import sample.domain.models.Record;
import sample.presentation.models.SearchParams;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

public class PharmStrategy extends Strategy {

    public static final String TABLE_NAME = "pharm";
    private static final GregorianCalendar CALENDAR = new GregorianCalendar();

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_PROVIDER = "provider";
    public static final String COLUMN_CERTIFICATE = "certificate";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_UPLOAD_TIME = "upload_time";

    private final String AND = "and";

    public PharmStrategy(Statement statement) {
        super(statement);
    }

    public List<Record> getRecords(int page, int count) throws Exception {
        LinkedList<Record> records = new LinkedList();
        ResultSet result = mStatement.executeQuery("SELECT * FROM "+TABLE_NAME+" ORDER BY "+COLUMN_UPLOAD_TIME+" DESC LIMIT "+count+" OFFSET "+getOffsetByPage(page, count)+";");
        while (result.next()) {
            Record record = SqlRecordMapper.mapRecord(result);
            records.add(record);
        }
        return records;
    }

    public List<Record> getRecords(SearchParams params) throws Exception {
        LinkedList<Record> records = new LinkedList();
        String sqlConditions = generateSqlConditions(params);
        ResultSet result = mStatement.executeQuery("SELECT * FROM "+TABLE_NAME+" "+sqlConditions+" ORDER BY "+COLUMN_UPLOAD_TIME+" DESC;");
        while (result.next()) {
            Record record = SqlRecordMapper.mapRecord(result);
            records.add(record);
        }
        return records;
    }

    public void putRecords(List<Record> records, Runnable run) throws Exception {
        for (Record record : records)
            try {
                run.run();
                mStatement.execute("INSERT INTO "+TABLE_NAME+" VALUES ("+SqlRecordMapper.mapRecord(record)+");");
            } catch (Exception e) {

            }
    }

    public List<Record> checkRecords(List<Record> records) throws Exception {
        List<Record> existsRecords = new LinkedList<>();
        for (Record record : records) {
            ResultSet result = mStatement.executeQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_ID+" = '"+record.getId()+"';");
            if (result.next())
                existsRecords.add(record);
        }
        return existsRecords;
    }

    public void deleteRecords(List<Record> records, Runnable run) {
        for (Record record : records)
            try {
                run.run();
                mStatement.execute("DELETE FROM "+TABLE_NAME+" WHERE "+COLUMN_ID+" = '"+record.getId()+"';");
            } catch (Exception e) {

            }
    }

    private int getOffsetByPage(int page, int count) {
        return (page-1)*count;
    }

    private String generateSqlConditions(SearchParams params) {
        StringBuilder conditions = new StringBuilder();
        if (!params.getId().isEmpty())
            conditions.append(COLUMN_ID+" = '"+params.getId()+"'");
        if (!params.getTitle().isEmpty()) {
            addAndTag(conditions);
            conditions.append(COLUMN_TITLE+" ~*'" + params.getTitle() + "'");
        }
        if (!params.getProvider().isEmpty()) {
            addAndTag(conditions);
            conditions.append(COLUMN_PROVIDER+" ~*'" + params.getProvider() + "'");
        }

        if (!params.getDateFrom().isEmpty()) {
            addAndTag(conditions);
            conditions.append(COLUMN_UPLOAD_TIME+" >= " + getLongDateFromStringFrom(params.getDateFrom()) + "");
        }

        if (!params.getDateTo().isEmpty()) {
            addAndTag(conditions);
            conditions.append(COLUMN_UPLOAD_TIME+" <= " + getLongDateFromStringTo(params.getDateTo()) + "");
        }

        if (conditions.length()>0)
            return "WHERE "+conditions.toString();
        else
            return conditions.toString();
    }

    private StringBuilder addAndTag(StringBuilder stringBuilder) {
        if (stringBuilder.length()>0)
            stringBuilder.append(" "+AND+" ");
        return stringBuilder;
    }

    private long getLongDateFromStringFrom(String date) {
        String[] dateValues = date.split("\\.");
        CALENDAR.set(Calendar.HOUR, 0);
        CALENDAR.set(Calendar.MINUTE, 0);
        CALENDAR.set(Calendar.SECOND, 0);
        CALENDAR.set(Calendar.MILLISECOND, 0);
        CALENDAR.set(Integer.parseInt(dateValues[2]), (Integer.parseInt(dateValues[1])-1), Integer.parseInt(dateValues[0]));
        return CALENDAR.getTimeInMillis();
    }

    private long getLongDateFromStringTo(String date) {
        String[] dateValues = date.split("\\.");
        CALENDAR.set(Calendar.HOUR, 23);
        CALENDAR.set(Calendar.MINUTE, 59);
        CALENDAR.set(Calendar.SECOND, 59);
        CALENDAR.set(Calendar.MILLISECOND, 999);
        CALENDAR.set(Integer.parseInt(dateValues[2]), (Integer.parseInt(dateValues[1])-1), Integer.parseInt(dateValues[0]));
        return CALENDAR.getTimeInMillis();
    }
}
