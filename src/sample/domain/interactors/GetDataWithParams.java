package sample.domain.interactors;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import rx.Observable;
import rx.Subscriber;
import sample.domain.interfaces.Interactor1;
import sample.domain.interfaces.Repository;
import sample.domain.models.Record;
import sample.presentation.Main;
import sample.presentation.models.SearchParams;

public class GetDataWithParams implements Interactor1<ObservableList<Record>, SearchParams> {

    private Repository mRepository;

    public GetDataWithParams() {
        mRepository = Main.getRepository();
    }

    @Override
    public Observable<ObservableList<Record>> execute(final SearchParams data) {
        Observable.OnSubscribe<ObservableList<Record>> observer = new Observable.OnSubscribe<ObservableList<Record>>() {
            @Override
            public void call(Subscriber<? super ObservableList<Record>> subscriber) {
                ObservableList<Record> records = FXCollections.observableArrayList();
                try {
                    records.addAll(mRepository.getRecords(data));
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
