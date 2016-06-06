package sample.data.db_store.transaction_strategy;


import sample.domain.models.Record;

import java.sql.Statement;
import java.util.List;

public abstract class Strategy {

    protected Statement mStatement;

    public Strategy(Statement statement) {
        mStatement = statement;
    }
}
