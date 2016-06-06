package sample.domain.interfaces;

import rx.Observable;


public interface Interactor2<T, I, I2> extends BaseInteractor {

    Observable<T> execute(I data, I2 data2);
}