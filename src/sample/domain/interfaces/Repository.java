package sample.domain.interfaces;


import sample.domain.models.CheckCollections;
import sample.domain.models.Record;
import sample.presentation.models.SearchParams;

import java.util.List;

public interface Repository {

    List<Record> getRecords(int page, int count) throws Exception;
    void putRecords(List<Record> records, Runnable run) throws Exception;
    CheckCollections checkRecords(List<Record> records, Runnable run) throws Exception;
    List<Record> getRecords(SearchParams params) throws Exception;
    void deleteRecords(List<Record> records, Runnable run) throws Exception;
    void putPreferense(String key, String value) throws Exception;
    String getPreferense(String key) throws Exception;
    void deletePreferense(String key) throws Exception;
    void addRecord(Record record) throws Exception;
}
