package arca.knote.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import arca.knote.R
import arca.knote.model.Note
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_note.*

class NoteActivity : AppCompatActivity() {
    private var note: Note = Note()
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        realm = Realm.getDefaultInstance()

        if(intent.hasExtra("note_id")) {
            val n = realm.where(Note::class.java).equalTo("id", intent.getIntExtra("note_id", -1)).findFirst()
            if (n == null) {
                Toast.makeText(this, "Не удалось загрузить заметку", Toast.LENGTH_SHORT).show()
                finish()
                return
            }
            name.setText(n.title)
            data.setText(n.text)
            note = n
        }

        cancel.setOnClickListener { finish() }

        save.setOnClickListener{
            when {
                name.text.isEmpty() -> Toast.makeText(this, "Не указан заголовок заметки...", Toast.LENGTH_SHORT).show()
                data.text.isEmpty() -> Toast.makeText(this, "Заметка пуста...", Toast.LENGTH_SHORT).show()
                else -> {
                    if(note.id == -1)
                        note.id = realm.where(Note::class.java).findAll().size
                    note.title = name.text.toString()
                    note.text = data.text.toString()
                    note.date = System.currentTimeMillis()
                    realm.executeTransaction { realm.copyToRealmOrUpdate(note) }
                    finish()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}