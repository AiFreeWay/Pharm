package sample.domain.interactors;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import rx.Observable;
import rx.Subscriber;
import sample.domain.interfaces.Interactor2;
import sample.domain.models.Record;
import sample.domain.utils.RecordMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;


public class Download implements Interactor2<Void, List<Record>, File> {

    @Override
    public Observable<Void> execute(final List<Record> data, final File data2) {
        Observable.OnSubscribe<Void> observer = new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    Workbook myExcelBook = WorkbookFactory.create(data2);
                    Sheet myExcelSheet = myExcelBook.createSheet();
                    for (int i=0; i<data.size(); i++) {
                        Row row = myExcelSheet.createRow(i);
                        Record record = data.get(i);
                        RecordMapper.fillRow(record, row);
                    }
                    myExcelBook.write(new FileOutputStream(data2));
                    myExcelBook.close();
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
