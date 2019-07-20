package arca.knote.mvp.views

import arca.knote.mvp.model.Note
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
interface MainView : MvpView {
    @StateStrategyType(AddToEndSingleStrategy::class)
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