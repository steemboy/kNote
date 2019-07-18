package arca.knote.mvp.views

import arca.knote.mvp.model.Note
import com.arellomobile.mvp.MvpView

interface MainView : MvpView {
    fun onNotesLoaded(notes: ArrayList<Note>)
    fun onAllNotesDeleted()
    fun showNoteDeleteDialog(text: String)
    fun hideNoteDeleteDialog()

    fun onNoteChange(n: Note?)
    fun onNotesDelete(pos: IntArray)
    fun onNoteInsert(n: Note?)

    fun onSetSelect(state: Boolean)
    fun onSelect(sel: Boolean, pos: Int)

    fun onClose()
}