package arca.knote.model

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import arca.knote.R

class NoteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var mNoteTitle: TextView = itemView.findViewById(R.id.name) as TextView
    var mNoteDate: TextView = itemView.findViewById(R.id.date) as TextView
    var mNoteText: TextView = itemView.findViewById(R.id.data) as TextView
}