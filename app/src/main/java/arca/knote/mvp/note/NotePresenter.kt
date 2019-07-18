package arca.knote.mvp.note

import android.content.Intent
import arca.knote.mateShortToast
import arca.knote.model.Note
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.realm.Realm

@InjectViewState
class NotePresenter : MvpPresenter<NoteView>() {
    private var realm: Realm = Realm.getDefaultInstance()
    private var note: Note = Note()

    fun onCreate(intent: Intent) {
        if(intent.hasExtra("note_id")) {
            val n = realm.where(Note::class.java).equalTo("id", intent.getIntExtra("note_id", -1)).findFirst()
            if (n == null) {
                mateShortToast("Не удалось загрузить заметку")
                viewState.onClose()
                return
            }
            note = n
            viewState.setData(note.title, note.text)
        }
    }

    fun onSave(name: String, text: String) {
        when {
            name.isEmpty() -> mateShortToast("Не указан заголовок заметки...")
            text.isEmpty() -> mateShortToast("Заметка пуста...")
            else -> {
                realm.executeTransaction {
                    if (note.id == -1)
                        note.id = realm.where(Note::class.java).findAll().size
                    note.title = name
                    note.text = text
                    note.date = System.currentTimeMillis()
                    realm.copyToRealmOrUpdate(note)
                }
                viewState.onClose()
            }
        }
    }

    fun onCloseButton() {
        viewState.onClose()
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}