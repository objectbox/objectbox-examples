package io.objectbox.example;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.ObjectBoxLiveData;

public class NoteViewModel extends AndroidViewModel {

    private ObjectBoxLiveData<Note> noteLiveData;

    public NoteViewModel(Application application) {
        super(application);
    }

    public ObjectBoxLiveData<Note> getNoteLiveData() {
        if (noteLiveData == null) {
            BoxStore boxStore = ((App) getApplication()).getBoxStore();
            Box<Note> notesBox = boxStore.boxFor(Note.class);
            // query all notes, sorted a-z by their text (http://greenrobot.org/objectbox/documentation/queries/)
            noteLiveData = new ObjectBoxLiveData<>(notesBox.query().order(Note_.text).build());
        }
        return noteLiveData;
    }
}
