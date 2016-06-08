package sample.domain.interactors;


import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import rx.Observable;
import rx.Subscriber;
import sample.domain.interfaces.Interactor2;
import sample.domain.interfaces.Repository;
import sample.domain.models.Record;
import sample.domain.utils.RecordMapper;
import sample.presentation.Main;

import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;

public class LoadFile implements Interactor2<List<Record>, String, Runnable> {

    private final int FIRST_SHEET = 0;

    private Repository mRepository;

    public LoadFile() {
        mRepository = Main.getRepository();
    }

    @Override
    public Observable<List<Record>> execute(final String path, final Runnable run) {
        final List<Record> records = new LinkedList<>();
        Observable.OnSubscribe<List<Record>> observer = new Observable.OnSubscribe<List<Record>>() {
            @Override
            public void call(Subscriber<? super List<Record>> subscriber) {
                try {
                    HSSFWorkbook myExcelBook = new HSSFWorkbook(new FileInputStream(path));
                    HSSFSheet myExcelSheet = myExcelBook.getSheetAt(FIRST_SHEET);
                    for (int i=0; i<=myExcelSheet.getLastRowNum(); i++) {
                        Row row = myExcelSheet.getRow(i);
                        Record record = RecordMapper.mapRecord(row);
                        if (!record.getId().isEmpty())
                            records.add(record);
                    }
                    myExcelBook.close();
                    subscriber.onNext(records);
                    mRepository.putRecords(records, run);
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
