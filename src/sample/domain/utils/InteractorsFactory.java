package sample.domain.utils;

import sample.domain.interactors.*;
import sample.domain.interfaces.BaseInteractor;


public class InteractorsFactory {

    private BaseInteractor[] mInteractors = new BaseInteractor[InteractorsFactory.Interactors.values().length];

    public InteractorsFactory() {
        mInteractors[Interactors.GET_DATA.id] = new GetData();
        mInteractors[Interactors.LOAD_FILE.id] = new LoadFile();
        mInteractors[Interactors.CHECK_FILE.id] = new CheckFile();
        mInteractors[Interactors.GET_DATA_WITH_PARAMS.id] = new GetDataWithParams();
        mInteractors[Interactors.DOWNLOAD.id] = new Download();
        mInteractors[Interactors.DELETE.id] = new Delete();
        mInteractors[Interactors.GET_PREFERENSE.id] = new GetPreferense();
        mInteractors[Interactors.PUT_PREFERENSE.id] = new PutPreferense();
        mInteractors[Interactors.DELETE_PREFERENSE.id] = new DeletePreferese();
        mInteractors[Interactors.ADD_RECORD.id] = new AddRecord();
    }

    public BaseInteractor getInteractor(Interactors interactorType) {
        return mInteractors[interactorType.id];
    }

    public enum Interactors {
        GET_DATA(0),
        LOAD_FILE(1),
        CHECK_FILE(2),
        GET_DATA_WITH_PARAMS(3),
        DOWNLOAD(4),
        DELETE(5),
        GET_PREFERENSE(6),
        PUT_PREFERENSE(7),
        DELETE_PREFERENSE(8),
        ADD_RECORD(9);

        int id;

        Interactors(int id) {
            this.id = id;
        }
    }
}
