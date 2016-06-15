package sample.domain.interactors;


import rx.Observable;
import rx.Subscriber;
import sample.domain.interfaces.Interactor1;
import sample.domain.interfaces.Repository;
import sample.presentation.Main;

public class GetPreferense implements Interactor1<String, String> {

    private Repository mRepository;

    public GetPreferense() {
        mRepository = Main.getRepository();
    }

    @Override
    public Observable<String> execute(final String data) {
        Observable.OnSubscribe<String> observer = new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String value = mRepository.getPreferense(data);
                    subscriber.onNext(value);
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
