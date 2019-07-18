package arca.knote.mvp.main

import android.content.Intent
import arca.knote.NoteApplication
import arca.knote.activities.NoteActivity
import arca.knote.mateShortToast
import arca.knote.model.Note
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.realm.OrderedCollectionChangeSet
import io.realm.Realm
import io.realm.RealmResults

@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {
    private var notes: RealmResults<Note>
    private var note: Note? = null
    private var realm: Realm = Realm.getDefaultInstance()

    init {
        notes = realm.where(Note::class.java).findAll()
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
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    fun onStart() {
        viewState.onNotesLoaded(ArrayList(notes))
    }

    fun onFubClick() {
        NoteApplication.instance.startActivity(Intent(NoteApplication.instance, NoteActivity::class.java))
    }

    fun onItemClick(pos: Int) {
        note = notes[pos]
        NoteApplication.instance.startActivity(Intent(NoteApplication.instance, NoteActivity::class.java).putExtra("note_id", note!!.id))
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
        realm.executeTransaction {
            if(note == null)
                notes.deleteAllFromRealm()
            else
                note!!.deleteFromRealm()
        }
        note = null
    }

    fun onDeleteDialogCancel() {
        viewState.hideNoteDeleteDialog()
    }

    fun onDeleteDialogDismiss() {
        viewState.hideNoteDeleteDialog()
    }
}