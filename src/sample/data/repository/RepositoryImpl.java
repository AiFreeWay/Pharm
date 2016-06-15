package sample.data.repository;


import sample.data.db_store.DbController;
import sample.data.db_store.transaction_strategy.PharmStrategy;
import sample.data.db_store.transaction_strategy.SharedPreferenseStrategy;
import sample.data.utils.DbStrategyesFactory;
import sample.domain.interfaces.Repository;
import sample.domain.models.Record;
import sample.presentation.models.SearchParams;

import java.util.List;

public class RepositoryImpl implements Repository {

    private DbController mDbController;
    private PharmStrategy mPharmStrategy;
    private SharedPreferenseStrategy mSharedPreferenseStrategy;

    public RepositoryImpl() {
        mDbController = new DbController();
        mPharmStrategy = (PharmStrategy) mDbController.getStrategy(DbStrategyesFactory.TransactionStrategyes.PHARM);
        mSharedPreferenseStrategy = (SharedPreferenseStrategy) mDbController.getStrategy(DbStrategyesFactory.TransactionStrategyes.PREFERENSE);
    }

    public List<Record> getRecords(int page, int count) throws Exception {
        return mPharmStrategy.getRecords(page, count);
    }

    @Override
    public void putRecords(List<Record> records, Runnable run) throws Exception {
        mPharmStrategy.putRecords(records, run);
    }

    @Override
    public List<Record> checkRecords(List<Record> records) throws Exception {
        return mPharmStrategy.checkRecords(records);
    }

    @Override
    public List<Record> getRecords(SearchParams params) throws Exception {
        return mPharmStrategy.getRecords(params);
    }

    @Override
    public void deleteRecords(List<Record> records, Runnable run) throws Exception {
        mPharmStrategy.deleteRecords(records, run);
    }

    @Override
    public void putPreferense(String key, String value) throws Exception {
        mSharedPreferenseStrategy.put(key, value);
    }

    @Override
    public String getPreferense(String key) throws Exception {
        return mSharedPreferenseStrategy.get(key);
    }

    @Override
    public void deletePreferense(String key) throws Exception {
        mSharedPreferenseStrategy.delete(key);
    }
}
