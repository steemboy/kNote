package arca.knote.mvp.views

import com.arellomobile.mvp.MvpView

interface NoteView : MvpView {
    fun onClose()
    fun setData(name: String, text: String)
}