package arca.knote.mvp.presenters

import android.content.Intent
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
    private var note: Note? = null

    init {
        appComponent.inject(this)
        notes = nHelper.getAll()
        notes.addChangeListener { t: RealmResults<Note>, c: OrderedCollectionChangeSet ->
            when {
                c.changes.isNotEmpty() -> viewState.onNoteChange(t[c.changes[0]])
                c.deletions.isNotEmpty() -> run {
                    if(c.deletions.size == 1)
                        viewState.onNoteDelete(c.deletions[0])
                    else
                        viewState.onAllNotesDeleted()
                }
                c.insertions.isNotEmpty() -> viewState.onNoteInsert(t[c.insertions[0]])
            }
        }
        viewState.onNotesLoaded(ArrayList(notes))
    }

    override fun onDestroy() {
        super.onDestroy()
        nHelper.close()
    }

    fun onFubClick() {
        instance.startActivity(Intent(instance, NoteActivity::class.java))
    }

    fun onItemClick(pos: Int) {
        note = notes[pos]
        instance.startActivity(Intent(instance, NoteActivity::class.java).putExtra("note_id", note!!.id))
    }

    fun onItemLongClick(pos: Int) {
        note = notes[pos]
        viewState.showNoteDeleteDialog("Удалить заметку " + note?.title + "?")
    }

    fun onDeleteAllClick() : Boolean {
        note = null
        if(notes.size > 0)
            viewState.showNoteDeleteDialog("Удалить все заметки?")
        else
            mateShortToast("Нет заметок")
        return true
    }

    fun onDeleteDialogOK() {
        viewState.hideNoteDeleteDialog()
        if(note == null)
            nHelper.deleteAll()
        else
            nHelper.delete(note!!)
        note = null
    }

    fun onDeleteDialogCancel() {
        viewState.hideNoteDeleteDialog()
    }

    fun onDeleteDialogDismiss() {
        viewState.hideNoteDeleteDialog()
    }
}