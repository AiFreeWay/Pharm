package sample.domain.interactors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import rx.Observable;
import rx.Subscriber;
import sample.domain.interfaces.Interactor2;
import sample.domain.interfaces.Repository;
import sample.domain.models.Record;
import sample.presentation.Main;


public class GetData implements Interactor2<ObservableList<Record>, Integer, Integer> {

    private Repository mRepository;

    public GetData() {
        mRepository = Main.getRepository();
    }

    @Override
    public Observable<ObservableList<Record>> execute(final Integer page, final Integer count) {
        Observable.OnSubscribe<ObservableList<Record>> observer = new Observable.OnSubscribe<ObservableList<Record>>() {
            @Override
            public void call(Subscriber<? super ObservableList<Record>> subscriber) {
                ObservableList<Record> records = FXCollections.observableArrayList();
                try {
                    records.addAll(mRepository.getRecords(page, count));
                    subscriber.onNext(records);
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
