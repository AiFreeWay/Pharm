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
        DELETE(5);

        int id;

        Interactors(int id) {
            this.id = id;
        }
    }
}
