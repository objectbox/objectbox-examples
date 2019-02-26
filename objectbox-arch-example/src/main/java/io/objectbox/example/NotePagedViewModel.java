package io.objectbox.example;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import io.objectbox.Box;
import io.objectbox.android.ObjectBoxDataSource;
import io.objectbox.query.Query;

public class NotePagedViewModel extends ViewModel {

    private LiveData<PagedList<Note>> noteLiveDataPaged;

    public LiveData<PagedList<Note>> getNoteLiveDataPaged(Box<Note> notesBox) {
        if (noteLiveDataPaged == null) {
            // query all notes, sorted a-z by their text (https://docs.objectbox.io/queries)
            Query<Note> query = notesBox.query().order(Note_.text).build();
            noteLiveDataPaged = new LivePagedListBuilder<>(
                    new ObjectBoxDataSource.Factory<>(query),
                    20
            ).build();
        }
        return noteLiveDataPaged;
    }
}
