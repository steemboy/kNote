package arca.knote.activities

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import arca.knote.R
import arca.knote.adapter.NoteAdapter
import arca.knote.adapter.RecyclerItemClickListener
import arca.knote.mvp.model.Note
import arca.knote.mvp.presenters.MainPresenter
import arca.knote.mvp.views.MainView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : RecyclerItemClickListener.OnItemClickListener, MvpAppCompatActivity(),
    MainView {
    @InjectPresenter
    lateinit var mPresenter: MainPresenter
    private lateinit var dialogBuilder: AlertDialog.Builder
    private var noteAdapter: NoteAdapter = NoteAdapter()
    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list.adapter = noteAdapter
        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        list.layoutManager = manager
        list.addItemDecoration(DividerItemDecoration(this, manager.orientation))
        list.addOnItemTouchListener(RecyclerItemClickListener(this, list))

        fab.setOnClickListener { mPresenter.onFubClick() }

        dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setOnDismissListener { mPresenter.onDeleteDialogDismiss() }
        dialogBuilder.setPositiveButton("Да") { _, _ -> mPresenter.onDeleteDialogOK() }
        dialogBuilder.setNegativeButton("Нет") { _, _ ->  mPresenter.onDeleteDialogCancel() }
        dialogBuilder.setCancelable(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete -> mPresenter.onDeleteAllClick()
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showNoteDeleteDialog(text: String) {
        dialogBuilder.setMessage(text)
        dialog = dialogBuilder.create()
        dialog!!.show()
    }

    override fun onNotesLoaded(notes: ArrayList<Note>) = (list.adapter as NoteAdapter).addAll(notes)
    override fun onAllNotesDeleted() = (list.adapter as NoteAdapter).removeAll()
    override fun hideNoteDeleteDialog() = dialog!!.dismiss()

    override fun onItemClick(view: View, position: Int) =  mPresenter.onItemClick(position)
    override fun onItemLongClick(view: View, position: Int) = mPresenter.onItemLongClick(position)

    override fun onNoteChange(n: Note?) =  (list.adapter as NoteAdapter).change(n!!)
    override fun onNoteDelete(pos: Int) =  (list.adapter as NoteAdapter).remove(pos)
    override fun onNoteInsert(n: Note?) = (list.adapter as NoteAdapter).add(n!!)
}
