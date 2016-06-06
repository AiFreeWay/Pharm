package sample.domain.interactors;

import rx.Observable;
import rx.Subscriber;
import sample.domain.interfaces.Interactor2;
import sample.domain.interfaces.Repository;
import sample.domain.models.Record;
import sample.presentation.Main;

import java.util.List;


public class Delete implements Interactor2<Void, List<Record>, Runnable> {

    private Repository mRepository;

    public Delete() {
        mRepository = Main.getRepository();
    }

    @Override
    public Observable<Void> execute(final List<Record> data, final Runnable run) {
        Observable.OnSubscribe observer = new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    mRepository.deleteRecords(data, run);
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
