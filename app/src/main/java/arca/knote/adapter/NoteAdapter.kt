package arca.knote.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import arca.knote.R
import arca.knote.formatDate
import arca.knote.mvp.model.Note

class NoteAdapter : RecyclerView.Adapter<NoteHolder>() {
    private var mNotesList: ArrayList<Note> = ArrayList()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NoteHolder {
        return NoteHolder(LayoutInflater.from(p0.context).inflate(R.layout.temp_note, p0, false))
    }

    override fun getItemCount(): Int {
        return mNotesList.size
    }

    override fun onBindViewHolder(p0: NoteHolder, p1: Int) {
        val note = mNotesList[p1]
        p0.mNoteTitle.text = note.title
        p0.mNoteText.text = note.text
        p0.mNoteDate.text = formatDate(note.date)
    }

    fun add(n: Note) {
        if (!mNotesList.contains(n))
            mNotesList.add(n)
    }

    fun remove(id: Int) {
        if(id < mNotesList.size && id > -1) {
            notifyItemRemoved(id)
            mNotesList.removeAt(id)
        }
    }

    fun change(note: Note) {
        for(i in 0 until mNotesList.size)
            if (mNotesList[i].id == note.id) {
                mNotesList[i] = note
                notifyItemChanged(i)
                break
            }
    }

    fun removeAll() {
        mNotesList.clear()
        notifyDataSetChanged()
    }

    fun addAll(notes: ArrayList<Note>) {
        mNotesList.clear()
        mNotesList.addAll(notes)
        notifyDataSetChanged()
    }
}
