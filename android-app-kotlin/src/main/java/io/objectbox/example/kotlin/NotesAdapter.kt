package io.objectbox.example.kotlin

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
