package arca.knote.mvp.views

import arca.knote.mvp.model.Note
import com.arellomobile.mvp.MvpView

interface MainView : MvpView {
    fun onNotesLoaded(notes: ArrayList<Note>)
    fun onAllNotesDeleted()
    fun showNoteDeleteDialog(text: String)
    fun hideNoteDeleteDialog()

    fun onNoteChange(n: Note?)
    fun onNoteDelete(pos: Int)
    fun onNoteInsert(n: Note?)
}