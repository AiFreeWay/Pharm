package sample.domain.interfaces;


import rx.Observable;

public interface Interactor1<T, I> extends BaseInteractor {

    Observable<T> execute(I data);
}
