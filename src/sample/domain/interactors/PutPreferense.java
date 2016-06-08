package sample.domain.interactors;

import rx.Observable;
import rx.Subscriber;
import sample.domain.interfaces.Interactor2;
import sample.domain.interfaces.Repository;
import sample.presentation.Main;


public class PutPreferense implements Interactor2<Void, String, String> {

    private Repository mRepository;

    public PutPreferense() {
        mRepository = Main.getRepository();
    }

    @Override
    public Observable<Void> execute(final String data, final String data2) {
        Observable.OnSubscribe<Void> observer = new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    mRepository.putPreferense(data, data2);
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