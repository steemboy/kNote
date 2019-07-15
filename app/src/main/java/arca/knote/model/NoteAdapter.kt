package arca.knote.model

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import arca.knote.NoteApplication
import arca.knote.R
import arca.knote.classes.formatDate

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

    public fun add(n: Note) {
        if (!mNotesList.contains(n))
            mNotesList.add(n)
    }

    public fun remove(id: Int) {
        if(id < mNotesList.size && id > -1) {
            notifyItemRemoved(id)
            mNotesList.removeAt(id)
            NoteApplication.instance.dbHelper.removeNote(id)
        }
    }

    public fun remove(n: Note) {
        if(mNotesList.contains(n)) {
            notifyItemRemoved(mNotesList.indexOf(n))
            mNotesList.remove(n)
            NoteApplication.instance.dbHelper.removeNote(n)
        }
    }

    public fun removeAll(notes: ArrayList<Note>) {
        for(n in notes)
            remove(n)
    }

    public fun removeAll() {
        NoteApplication.instance.dbHelper.removeNotes(mNotesList)
        mNotesList.clear()
        notifyDataSetChanged()
    }

    public fun addAll(notes: ArrayList<Note>?) {
        mNotesList.clear()
        mNotesList.addAll(notes!!)
        notifyDataSetChanged()
    }

    public fun getNote(pos: Int) : Note {
        if(pos < mNotesList.size && pos >= 0)
            return mNotesList.get(pos)
        return Note()
    }
}
