package arca.knote.mvp.note

import com.arellomobile.mvp.MvpView

interface NoteView : MvpView {
    fun onClose()
    fun setData(name: String, text: String)
}