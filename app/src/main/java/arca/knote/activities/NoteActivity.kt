package arca.knote.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import arca.knote.NoteApplication
import arca.knote.R
import arca.knote.model.Note
import kotlinx.android.synthetic.main.activity_note.*

class NoteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)

        if(intent.hasExtra("note_id")) {
            val note = NoteApplication.instance.dbHelper.getNote(intent.getIntExtra("note_id", 0))
            name.setText(note.title)
            data.setText(note.text)
        }

        cancel.setOnClickListener {
            finish()
        }

        save.setOnClickListener{
            if(!data.text.isEmpty()) {
                NoteApplication.instance.dbHelper.addNote(Note().setTitle(name.text.toString()).setText(data.text.toString()))
                finish()
            } else
                Toast.makeText(this, "Ни чего не введено", Toast.LENGTH_SHORT).show()
        }
    }
}