package sample.domain.interactors;


import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import rx.Observable;
import rx.Subscriber;
import sample.domain.interfaces.Interactor1;
import sample.domain.interfaces.Interactor2;
import sample.domain.interfaces.Repository;
import sample.domain.models.CheckCollections;
import sample.domain.models.Record;
import sample.domain.utils.RecordMapper;
import sample.presentation.Main;

import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;

public class CheckFile implements Interactor2<CheckCollections, String, Runnable> {

    private final int FIRST_SHEET = 0;

    private Repository mRepository;

    public CheckFile() {
        mRepository = Main.getRepository();
    }

    @Override
    public Observable<CheckCollections> execute(final String path, final Runnable run) {
        Observable.OnSubscribe<CheckCollections> observer = new Observable.OnSubscribe<CheckCollections>() {
            @Override
            public void call(Subscriber<? super CheckCollections> subscriber) {
                try {
                    List<Record> records = new LinkedList<>();
                    Workbook myExcelBook = WorkbookFactory.create(new FileInputStream(path));
                    Sheet myExcelSheet = myExcelBook.getSheetAt(FIRST_SHEET);
                    for (int i = 0; i<=myExcelSheet.getLastRowNum(); i++) {
                        Row row = myExcelSheet.getRow(i);
                        Record record = RecordMapper.mapRecord(row);
                        records.add(record);
                    }
                    myExcelBook.close();
                    subscriber.onNext(mRepository.checkRecords(records, run));
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