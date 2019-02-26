package io.objectbox.example;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import java.text.DateFormat;
import java.util.Date;

import io.objectbox.Box;
import io.objectbox.example.arch.R;

/**
 * Example using the paging library (https://developer.android.com/topic/libraries/architecture/paging/)
 * with {@link io.objectbox.android.ObjectBoxDataSource} to display notes.
 */
public class NotePagedActivity extends FragmentActivity {

    private EditText editText;
    private View addNoteButton;

    private Box<Note> notesBox;
    private NotesPagedAdapter notesAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_paged);

        setUpViews();

        notesBox = ObjectBox.get().boxFor(Note.class);

        NotePagedViewModel model = ViewModelProviders.of(this).get(NotePagedViewModel.class);
        model.getNoteLiveDataPaged(notesBox).observe(this, new Observer<PagedList<Note>>() {
            @Override
            public void onChanged(@Nullable PagedList<Note> notes) {
                notesAdapter.submitList(notes);
            }
        });
    }

    protected void setUpViews() {
        RecyclerView recyclerView = findViewById(R.id.recyclerViewNotes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        notesAdapter = new NotesPagedAdapter(noteClickListener);
        recyclerView.setAdapter(notesAdapter);

        addNoteButton = findViewById(R.id.buttonAdd);
        addNoteButton.setEnabled(false);

        editText = findViewById(R.id.editTextNote);
        editText.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    addNote();
                    return true;
                }
                return false;
            }
        });
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean enable = s.length() != 0;
                addNoteButton.setEnabled(enable);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }

    public void onAddButtonClick(View view) {
        addNote();
    }

    private void addNote() {
        String noteText = editText.getText().toString();
        editText.setText("");

        DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        String comment = "Added on " + df.format(new Date());

        Note note = new Note();
        note.setText(noteText);
        note.setComment(comment);
        note.setDate(new Date());
        notesBox.put(note);
        Log.d(App.TAG, "Inserted new note, ID: " + note.getId());
    }

    NotesPagedAdapter.NoteClickListener noteClickListener = new NotesPagedAdapter.NoteClickListener() {
        @Override
        public void onNoteClick(@Nullable Note note) {
            if (note == null) {
                return;
            }
            notesBox.remove(note);
            Log.d(App.TAG, "Deleted note, ID: " + note.getId());
        }
    };

}