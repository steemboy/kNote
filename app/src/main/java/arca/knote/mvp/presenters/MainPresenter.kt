package arca.knote.mvp.presenters

import android.content.Intent
import arca.knote.R
import arca.knote.activities.NoteActivity
import arca.knote.appComponent
import arca.knote.instance
import arca.knote.mateShortToast
import arca.knote.mvp.model.Note
import arca.knote.mvp.model.NoteHelper
import arca.knote.mvp.views.MainView
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.realm.OrderedCollectionChangeSet
import io.realm.RealmResults
import javax.inject.Inject

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {
    @Inject
    lateinit var nHelper: NoteHelper
    private var notes: RealmResults<Note>
    private var sNotes: ArrayList<Note> = ArrayList()
    private var select: Boolean = false

    init {
        appComponent.inject(this)
        notes = nHelper.getAll()
        notes.addChangeListener { t: RealmResults<Note>, c: OrderedCollectionChangeSet ->
            when {
                c.changes.isNotEmpty() -> viewState.onNoteChange(t[c.changes[0]])
                c.deletions.isNotEmpty() ->  viewState.onNotesDelete(c.deletions)
                c.insertions.isNotEmpty() -> viewState.onNoteInsert(t[c.insertions[0]])
            }
        }
        viewState.onNotesLoaded(ArrayList(notes))
    }

    override fun onDestroy() {
        super.onDestroy()
        nHelper.close()
    }

    fun onBackClick() {
        if(select) {
            sNotes.clear()
            select = false
            viewState.onSetSelect(false)
        } else
            viewState.onClose()
    }

    fun onMenuSelect(id: Int) : Boolean {
        var ans = true
        when (id) {
            R.id.delete -> onDeleteAllClick()
            R.id.home -> onBackClick()
            else -> { ans = false }
        }
        return ans
    }

    fun onDeleteAllClick() {
        when {
            notes.size == sNotes.size -> viewState.showNoteDeleteDialog("Удалить все заметки?")
            sNotes.size == 0 -> mateShortToast("Заметки не выбранны")
            else -> viewState.showNoteDeleteDialog("Удалить выбранные заметки? Выбрано: " + sNotes.size)
        }
    }

    fun onDeleteDialogOK() {
        viewState.hideNoteDeleteDialog()
        nHelper.delete(sNotes)
        onBackClick()
    }

    fun onItemClick(pos: Int) {
        val n = notes[pos]!!
        if (!select)
            instance.startActivity(Intent(instance, NoteActivity::class.java).putExtra("note_id", n.date))
        else if (sNotes.contains(n)) {
            sNotes.remove(n)
            viewState.onSelect(false, pos)
        } else {
            sNotes.add(n)
            viewState.onSelect(true, pos)
        }
    }

    fun onItemLongClick(pos: Int) {
        select = true
        viewState.onSetSelect(true)
        viewState.onSelect(true, pos)
        sNotes.add(notes[pos]!!)
    }

    fun onFubClick() = instance.startActivity(Intent(instance, NoteActivity::class.java))
    fun onDeleteDialogCancel() = viewState.hideNoteDeleteDialog()
    fun onDeleteDialogDismiss() = viewState.hideNoteDeleteDialog()
    fun getState(): Boolean = select
}