package arca.knote.activities

import android.os.Bundle
import android.view.MenuItem
import arca.knote.R
import arca.knote.mvp.presenters.NotePresenter
import arca.knote.mvp.views.NoteView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.r0adkll.slidr.Slidr
import kotlinx.android.synthetic.main.activity_note.*


class NoteActivity : MvpAppCompatActivity(), NoteView {
    @InjectPresenter
    lateinit var nPresenter: NotePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        nPresenter.onCreate(intent)
        save.setOnClickListener{ nPresenter.onSave(name.text.toString(), data.text.toString()) }
        Slidr.attach(this)
    }

    override fun setData(name: String, text: String) {
        this.name.setText(name)
        this.data.setText(text)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == R.id.home) {
            nPresenter.onCloseButton()
            true
        } else
            super.onOptionsItemSelected(item)
    }

    override fun onClose() = finish()
    override fun onBackPressed() = nPresenter.onCloseButton()
}