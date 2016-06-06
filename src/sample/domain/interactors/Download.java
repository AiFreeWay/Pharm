package sample.domain.interactors;


import org.apache.poi.hssf.model.InternalWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import rx.Observable;
import rx.Subscriber;
import sample.domain.interfaces.Interactor2;
import sample.domain.models.Record;
import sample.domain.utils.RecordMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Download implements Interactor2<Void, List<Record>, File> {

    @Override
    public Observable<Void> execute(final List<Record> data, final File data2) {
        Observable.OnSubscribe<Void> observer = new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    HSSFWorkbook myExcelBook = new HSSFWorkbook();
                    HSSFWorkbook.create(InternalWorkbook.createWorkbook());
                    HSSFSheet myExcelSheet = myExcelBook.createSheet();
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
