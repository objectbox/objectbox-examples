package io.objectbox.example.kotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

import java.util.ArrayList

class NotesAdapter : BaseAdapter() {

    private var dataset: List<Note>? = null

    private class NoteViewHolder(itemView: View) {

        var text: TextView
        var comment: TextView

        init {
            text = itemView.findViewById(R.id.textViewNoteText) as TextView
            comment = itemView.findViewById(R.id.textViewNoteComment) as TextView
        }
    }

    init {
        this.dataset = ArrayList<Note>()
    }

    fun setNotes(notes: List<Note>) {
        dataset = notes
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: NoteViewHolder
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_note, parent, false)
            holder = NoteViewHolder(convertView)
            convertView!!.tag = holder
        } else {
            holder = convertView.tag as NoteViewHolder
        }

        val note = getItem(position)
        holder.text.text = note.getText()
        holder.comment.text = note.getComment()

        return convertView
    }

    override fun getCount(): Int {
        return dataset!!.size
    }

    override fun getItem(position: Int): Note {
        return dataset!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

}
