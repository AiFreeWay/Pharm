package sample.data.db_store.transaction_strategy;


import sample.data.utils.SqlRecordMapper;
import sample.domain.models.CheckCollections;
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
    public static final String COLUMN_SERIES_HASH = "series_hash";
    public static final String COLUMN_SERIES = "series";
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
        LinkedList<Record> records = new LinkedList<>();
        ResultSet result = mStatement.executeQuery("SELECT * FROM "+TABLE_NAME+" ORDER BY "+COLUMN_UPLOAD_TIME+" DESC LIMIT "+count+" OFFSET "+getOffsetByPage(page, count)+";");
        while (result.next()) {
            Record record = SqlRecordMapper.mapRecord(result);
            records.add(record);
        }
        return records;
    }

    public List<Record> getRecords(SearchParams params) throws Exception {
        LinkedList<Record> records = new LinkedList<>();
        String sqlConditions = generateSqlConditions(params);
        ResultSet result = mStatement.executeQuery("SELECT * FROM "+TABLE_NAME+" "+sqlConditions+" ORDER BY "+COLUMN_UPLOAD_TIME+" DESC;");
        while (result.next()) {
            Record record = SqlRecordMapper.mapRecord(result);
            records.add(record);
        }
        return records;
    }

    public void putRecords(List<Record> records, Runnable run) throws Exception {
        for (Record record : records) {
            run.run();
            ResultSet result = mStatement.executeQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_ID+" = "+record.getId()+";");
            if (!result.next())
                mStatement.execute("INSERT INTO "+TABLE_NAME+" ("+COLUMN_ID+", "+COLUMN_SERIES_HASH+", "+COLUMN_SERIES+", "+COLUMN_TITLE+", "+COLUMN_PROVIDER+", "+COLUMN_CERTIFICATE+", "+COLUMN_DATE+", "+COLUMN_DESCRIPTION+", "+COLUMN_UPLOAD_TIME+") VALUES ("+SqlRecordMapper.mapRecord(record)+");");
        }
    }

    public CheckCollections checkRecords(List<Record> records, Runnable run) throws Exception {
        CheckCollections checkCollections = new CheckCollections();
        List<Record> recordsFromDb = new LinkedList<>();
        List<Record> recordsFromFile = new LinkedList<>();
        for (Record record : records) {
            run.run();
            ResultSet result = mStatement.executeQuery("SELECT * FROM "+TABLE_NAME+" WHERE "+COLUMN_SERIES_HASH+" = "+record.getSeriesHash()+";");
            boolean isAddedToRecordsFileList = false;
            while (result.next()) {
                recordsFromDb.add(SqlRecordMapper.mapRecord(result));
                if (!isAddedToRecordsFileList) {
                    recordsFromFile.add(record);
                    isAddedToRecordsFileList = true;
                }
            }
        }
        checkCollections.setRecordsFromDB(recordsFromDb);
        checkCollections.setRecordsFromFile(recordsFromFile);
        return checkCollections;
    }

    public void deleteRecords(List<Record> records, Runnable run) {
        for (Record record : records)
            try {
                run.run();
                mStatement.execute("DELETE FROM "+TABLE_NAME+" WHERE "+COLUMN_ID+" = "+record.getId()+";");
            } catch (Exception e) {

            }
    }

    public void addRecord(Record record) throws Exception {
        mStatement.execute("INSERT INTO "+TABLE_NAME+" ("+COLUMN_ID+", "+COLUMN_SERIES_HASH+", "+COLUMN_SERIES+", "+COLUMN_TITLE+", "+COLUMN_PROVIDER+", "+COLUMN_CERTIFICATE+", "+COLUMN_DATE+", "+COLUMN_DESCRIPTION+", "+COLUMN_UPLOAD_TIME+") VALUES ("+SqlRecordMapper.mapRecord(record)+");");
    }

    private int getOffsetByPage(int page, int count) {
        return (page-1)*count;
    }

    private String generateSqlConditions(SearchParams params) {
        StringBuilder conditions = new StringBuilder();
        if (!params.getId().isEmpty())
            conditions.append(COLUMN_ID+" = "+params.getId());
        if (!params.getSeries().isEmpty()) {
            addAndTag(conditions);
            conditions.append(COLUMN_SERIES+" ~*'" + params.getSeries() + "'");
        }
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
        CALENDAR.set(Integer.parseInt(dateValues[2]), (Integer.parseInt(dateValues[1]))-1, Integer.parseInt(dateValues[0]));
        return CALENDAR.getTimeInMillis();
    }

    private long getLongDateFromStringTo(String date) {
        String[] dateValues = date.split("\\.");
        CALENDAR.set(Calendar.HOUR, 23);
        CALENDAR.set(Calendar.MINUTE, 59);
        CALENDAR.set(Calendar.SECOND, 59);
        CALENDAR.set(Calendar.MILLISECOND, 999);
        CALENDAR.set(Integer.parseInt(dateValues[2]), (Integer.parseInt(dateValues[1]))-1, Integer.parseInt(dateValues[0]));
        return CALENDAR.getTimeInMillis();
    }
}
