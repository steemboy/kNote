package arca.knote.mvp.main

import arca.knote.model.Note
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

interface MainView : MvpView {
    fun onNotesLoaded(notes: ArrayList<Note>)
    fun onAllNotesDeleted()
    fun showNoteDeleteDialog(text: String)
    fun hideNoteDeleteDialog()

    fun onNoteChange(n: Note?)
    fun onNoteDelete(pos: Int)
    fun onNoteInsert(n: Note?)
}