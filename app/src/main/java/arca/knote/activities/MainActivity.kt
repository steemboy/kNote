package arca.knote.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import arca.knote.R
import arca.knote.classes.LoaderInterface
import arca.knote.classes.NoteLoader
import arca.knote.model.Note
import arca.knote.model.NoteAdapter
import arca.knote.model.RecyclerItemClickListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : LoaderInterface, RecyclerItemClickListener.OnItemClickListener, AppCompatActivity() {

    override fun onItemClick(view: View, position: Int) {
        val n = (list.adapter as NoteAdapter).getNote(position)
        startActivity(Intent(this, NoteActivity::class.java).putExtra("note_id", n.id))
    }

    override fun onItemLongClick(view: View, position: Int) {
        val n = (list.adapter as NoteAdapter).getNote(position)
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Удаление")
        alertDialogBuilder.setMessage("Удалить заметку " + n.title + "?")
        alertDialogBuilder.setOnDismissListener { dialog -> dialog.dismiss() }
        alertDialogBuilder.setPositiveButton("Да") { _, _ ->  (list.adapter as NoteAdapter).remove(position) }
        alertDialogBuilder.setNegativeButton("Нет") { dialog, _ ->  dialog.dismiss() }
        alertDialogBuilder.setCancelable(true)
        alertDialogBuilder.create().show()
    }

    override fun onLoad(l: ArrayList<Note>?) {
        (list.adapter as NoteAdapter).addAll(l)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        list.adapter = NoteAdapter()
        list.setLayoutManager(manager)
        list.addItemDecoration(DividerItemDecoration(this, manager.getOrientation()))
        list.addOnItemTouchListener(RecyclerItemClickListener(this, list))
        fab.setOnClickListener {
            startActivity(Intent(this, NoteActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        NoteLoader(this).execute()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete -> showDi()
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDi() : Boolean {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("Удаление")
        alertDialogBuilder.setMessage("Удалить все заметки?")
        alertDialogBuilder.setOnDismissListener { dialog -> dialog.dismiss() }
        alertDialogBuilder.setPositiveButton("Да") { _, _ ->  (list.adapter as NoteAdapter).removeAll() }
        alertDialogBuilder.setNegativeButton("Нет") { dialog, _ ->  dialog.dismiss() }
        alertDialogBuilder.setCancelable(true)
        alertDialogBuilder.create().show()
        return true
    }
}
