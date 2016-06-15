package sample.domain.interactors;


import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import rx.Observable;
import rx.Subscriber;
import sample.domain.interfaces.Interactor1;
import sample.domain.interfaces.Repository;
import sample.domain.models.Record;
import sample.domain.utils.RecordMapper;
import sample.presentation.Main;

import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;

public class CheckFile implements Interactor1<List<Record>, String> {

    private final int FIRST_SHEET = 0;

    private Repository mRepository;

    public CheckFile() {
        mRepository = Main.getRepository();
    }

    @Override
    public Observable<List<Record>> execute(final String path) {
        final List<Record> records = new LinkedList<>();
        Observable.OnSubscribe<List<Record>> observer = new Observable.OnSubscribe<List<Record>>() {
            @Override
            public void call(Subscriber<? super List<Record>> subscriber) {
                try {

                    Workbook myExcelBook = WorkbookFactory.create(new FileInputStream(path));
                    Sheet myExcelSheet = myExcelBook.getSheetAt(FIRST_SHEET);
                    for (int i = 0; i<=myExcelSheet.getLastRowNum(); i++) {
                        Row row = myExcelSheet.getRow(i);
                        Record record = RecordMapper.mapRecord(row);
                        records.add(record);
                    }
                    myExcelBook.close();
                    List<Record> existsRecords = mRepository.checkRecords(records);
                    subscriber.onNext(existsRecords);
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