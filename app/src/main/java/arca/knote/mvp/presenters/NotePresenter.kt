package arca.knote.mvp.presenters

import android.content.Intent
import arca.knote.appComponent
import arca.knote.mateShortToast
import arca.knote.mvp.model.Note
import arca.knote.mvp.model.NoteHelper
import arca.knote.mvp.views.NoteView
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import javax.inject.Inject

@InjectViewState
class NotePresenter : MvpPresenter<NoteView>() {
    @Inject
    lateinit var nHelper: NoteHelper
    private var note: Note = Note()

    init {
        appComponent.inject(this)
    }

    fun onCreate(intent: Intent) {
        if(intent.hasExtra("note_id")) {
            val n = nHelper.getNote(intent.getIntExtra("note_id", -1))
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
                nHelper.addOrUpdate(note, name, text)
                viewState.onClose()
            }
        }
    }

    fun onCloseButton() {
        viewState.onClose()
    }

    override fun onDestroy() {
        super.onDestroy()
        nHelper.close()
    }
}