package sample.domain.interactors;

import rx.Observable;
import rx.Subscriber;
import sample.domain.interfaces.Interactor1;
import sample.domain.interfaces.Repository;
import sample.domain.models.Record;
import sample.presentation.Main;

public class AddRecord implements Interactor1<Void, Record> {

    private Repository mRepository;

    public AddRecord() {
        mRepository = Main.getRepository();
    }

    @Override
    public Observable<Void> execute(Record data) {
        Observable.OnSubscribe<Void> observer = new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    mRepository.addRecord(data);
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
                subscriber.onCompleted();
            }
        };
        return Observable.create(observer);
    }
}
