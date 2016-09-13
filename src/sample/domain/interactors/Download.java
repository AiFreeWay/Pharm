package sample.domain.interactors;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import rx.Observable;
import rx.Subscriber;
import sample.domain.interfaces.Interactor2;
import sample.domain.models.Record;
import sample.domain.utils.RecordMapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;


public class Download implements Interactor2<Void, List<Record>, File> {

    private static final String XLS_EXTENTION = "xls";

    @Override
    public Observable<Void> execute(final List<Record> data, final File data2) {
        Observable.OnSubscribe<Void> observer = new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    Workbook myExcelBook = createWorkbook(data2);
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

    private Workbook createWorkbook(File file) throws Exception {
        String path = file.getAbsolutePath();
        String extention = path.substring(path.length()-5).split("\\.")[1];
        if (extention.equals(XLS_EXTENTION))
            return new HSSFWorkbook();
        return new XSSFWorkbook();
    }
}
