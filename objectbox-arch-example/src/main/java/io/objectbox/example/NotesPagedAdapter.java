package io.objectbox.example;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import io.objectbox.example.arch.R;

public class NotesPagedAdapter extends PagedListAdapter<Note, NotesPagedAdapter.NoteViewHolder> {

    public interface NoteClickListener {
        void onNoteClick(@Nullable Note note);
    }

    @NonNull
    private final NoteClickListener noteClickListener;

    public NotesPagedAdapter(@NonNull NoteClickListener noteClickListener) {
        super(DIFF_CALLBACK);
        this.noteClickListener = noteClickListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view, noteClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = getItem(position);
        if (note != null) {
            holder.bindTo(note);
        } else {
            holder.clear();
        }
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public TextView comment;
        public Note note;

        public NoteViewHolder(View itemView, final NoteClickListener noteClickListener) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    noteClickListener.onNoteClick(note);
                }
            });
            text = itemView.findViewById(R.id.textViewNoteText);
            comment = itemView.findViewById(R.id.textViewNoteComment);
        }

        public void bindTo(Note note) {
            this.note = note;
            text.setText(note.getText());
            comment.setText(note.getComment());
        }

        public void clear() {
            // show placeholder
            text.setText(R.string.placeholder_text);
            comment.setText(null);
        }
    }

    private static final DiffUtil.ItemCallback<Note> DIFF_CALLBACK = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(Note oldItem, Note newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem == newItem;
        }
    };

}
