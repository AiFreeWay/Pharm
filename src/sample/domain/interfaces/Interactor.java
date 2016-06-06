package sample.domain.interfaces;


import rx.Observable;

public interface Interactor<T> extends BaseInteractor {

    Observable<T> execute();
}
