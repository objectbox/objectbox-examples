/*
 * Copyright 2024 ObjectBox Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.objectbox.example;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.example.daocompat.R;

public class NotesAdapter extends BaseAdapter {

    private List<Note> dataset;

    private static class NoteViewHolder {

        public TextView text;
        public TextView comment;

        public NoteViewHolder(View itemView) {
            text = itemView.findViewById(R.id.textViewNoteText);
            comment = itemView.findViewById(R.id.textViewNoteComment);
        }
    }

    public NotesAdapter() {
        this.dataset = new ArrayList<>();
    }

    public void setNotes(List<Note> notes) {
        dataset = notes;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NoteViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_note, parent, false);
            holder = new NoteViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (NoteViewHolder) convertView.getTag();
        }

        Note note = getItem(position);
        holder.text.setText(note.getText());
        holder.comment.setText(note.getComment());

        return convertView;
    }

    @Override
    public int getCount() {
        return dataset.size();
    }

    @Override
    public Note getItem(int position) {
        return dataset.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
