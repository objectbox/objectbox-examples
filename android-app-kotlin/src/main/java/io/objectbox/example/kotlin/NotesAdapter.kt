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

package io.objectbox.example.kotlin

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import java.text.DateFormat

class NotesAdapter : BaseAdapter() {

    private val dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM)

    private val dataset: MutableList<Note> = mutableListOf()

    private class NoteViewHolder(itemView: View) {
        val text: TextView = itemView.findViewById(R.id.textViewNoteText)
        val comment: TextView = itemView.findViewById(R.id.textViewNoteComment)
    }

    fun setNotes(notes: List<Note>) {
        dataset.apply {
            clear()
            addAll(notes)
        }
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(parent.context)
                .inflate(R.layout.item_note, parent, false).also {
                    it.tag = NoteViewHolder(it)
                }
        val holder = view.tag as NoteViewHolder

        val note = getItem(position)
        if (note != null) {
            holder.text.text = note.text
            holder.comment.text = parent.context.getString(
                    R.string.note_meta_format,
                    note.date?.let { dateFormat.format(it) } ?: "",
                    note.author.target.name
            )
        } else {
            holder.text.text = ""
            holder.comment.text = ""
        }

        return view
    }

    override fun getCount(): Int = dataset.size

    override fun getItem(position: Int): Note? {
        return if (position >= 0 && position < dataset.size) {
            dataset[position]
        } else {
            null
        }
    }

    override fun getItemId(position: Int): Long = dataset[position].id

}
