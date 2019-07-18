package arca.knote.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import arca.knote.R
import arca.knote.formatDate
import arca.knote.mvp.model.Note
import kotlinx.coroutines.runBlocking

class NoteAdapter : RecyclerView.Adapter<NoteHolder>() {
    private var notesList: ArrayList<Note> = ArrayList()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NoteHolder {
        return when (p1) {
            0 -> NoteHolder(LayoutInflater.from(p0.context).inflate(R.layout.temp_note, p0, false))
            else -> NoteHolder(LayoutInflater.from(p0.context).inflate(R.layout.temp_selected_note, p0, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        if(notesList[position].selected)
            return 1
        return 0
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(p0: NoteHolder, p1: Int) {
        val note = notesList[p1]
        p0.mNoteTitle.text = note.title
        p0.mNoteText.text = note.text
        p0.mNoteDate.text = formatDate(note.date)
    }

    fun add(n: Note) {
        if (!notesList.contains(n))
            notesList.add(n)
    }

    fun remove(ids: IntArray) = runBlocking{
        for (i in ids.size - 1 downTo 0)
            if (ids[i] < notesList.size && ids[i] > -1) {
                notifyItemRemoved(ids[i])
                notesList.removeAt(ids[i])
            }
    }

    fun change(note: Note) = runBlocking {
        for(i in 0 until notesList.size)
            if (notesList[i].date == note.date) {
                notesList[i] = note
                notifyItemChanged(i)
                break
            }
    }

    fun removeAll() = runBlocking  {
        notesList.clear()
        notifyDataSetChanged()
    }

    fun addAll(notes: ArrayList<Note>) = runBlocking {
        notesList.clear()
        notesList.addAll(notes)
        notifyDataSetChanged()
    }

    fun setSelecItem(sel: Boolean, pos: Int) {
        if(pos >= notesList.size && pos < 0)
            return
        notesList[pos].selected = sel
        notifyItemChanged(pos)
    }

    fun stopSelect() = runBlocking{
        for(n in notesList)
            n.selected = false
        notifyDataSetChanged()
    }
}
