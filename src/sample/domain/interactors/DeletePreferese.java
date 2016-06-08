package sample.domain.interactors;

import rx.Observable;
import rx.Subscriber;
import sample.domain.interfaces.Interactor1;
import sample.domain.interfaces.Repository;
import sample.presentation.Main;


public class DeletePreferese implements Interactor1<Void, String> {

    private Repository mRepository;

    public DeletePreferese() {
        mRepository = Main.getRepository();
    }

    @Override
    public Observable<Void> execute(final String data) {
        Observable.OnSubscribe<Void> observer = new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    mRepository.deletePreferense(data);
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

