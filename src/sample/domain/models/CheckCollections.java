package sample.domain.models;


import java.util.List;

public class CheckCollections {

    List<Record> recordsFromDB;
    List<Record> recordsFromFile;

    public CheckCollections() {
    }

    public CheckCollections(List<Record> recordsFromDB, List<Record> recordsFromFile) {
        this.recordsFromDB = recordsFromDB;
        this.recordsFromFile = recordsFromFile;
    }

    public List<Record> getRecordsFromDB() {
        return recordsFromDB;
    }

    public void setRecordsFromDB(List<Record> recordsFromDB) {
        this.recordsFromDB = recordsFromDB;
    }

    public List<Record> getRecordsFromFile() {
        return recordsFromFile;
    }

    public void setRecordsFromFile(List<Record> recordsFromFile) {
        this.recordsFromFile = recordsFromFile;
    }
}
