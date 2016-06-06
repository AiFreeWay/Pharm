package sample.data.utils;


import sample.data.db_store.transaction_strategy.PharmStrategy;
import sample.data.db_store.transaction_strategy.Strategy;

import java.sql.Statement;


public class DbStrategyesFactory {

    private Strategy[] mStrategyes = new Strategy[TransactionStrategyes.values().length];

    public DbStrategyesFactory(Statement statement) {
        mStrategyes[TransactionStrategyes.PHARM.id] = new PharmStrategy(statement);
    }

    public Strategy getStrategy(TransactionStrategyes strategyType) {
        return mStrategyes[strategyType.id];
    }

    public enum TransactionStrategyes {
        PHARM(0);

        public int id;

        TransactionStrategyes(int id) {
            this.id = id;
        }
    }
}
