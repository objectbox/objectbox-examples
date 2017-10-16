package io.objectbox.example;

import android.arch.lifecycle.ViewModel;

import io.objectbox.Box;
import io.objectbox.android.ObjectBoxLiveData;

public class NoteViewModel extends ViewModel {

    private ObjectBoxLiveData<Note> noteLiveData;

    public ObjectBoxLiveData<Note> getNoteLiveData(Box<Note> notesBox) {
        if (noteLiveData == null) {
            // query all notes, sorted a-z by their text (http://greenrobot.org/objectbox/documentation/queries/)
            noteLiveData = new ObjectBoxLiveData<>(notesBox.query().order(Note_.text).build());
        }
        return noteLiveData;
    }
}
