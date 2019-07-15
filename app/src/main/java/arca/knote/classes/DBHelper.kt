package arca.knote.classes

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import arca.knote.NoteApplication.Companion.instance
import arca.knote.model.Note

class DBHelper : SQLiteOpenHelper(instance, "noteBase.db", null, 1) {
    private var TABLE_NAME: String = "note_table"
    private val CREATE: String = "CREATE TABLE IF NOT EXISTS "
    private var SEL: String = "SELECT * FROM "
    private var S: String = ","
    private var TXT: String =  " TEXT NOT NULL"
    private var INT: String =  " INTEGER NOT NULL"
    private var START: String = " (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
    private var END: String = ");"
    private var DELETE: String = "DROP TABLE IF EXISTS "
    private var WHERE: String = " WHERE "

    override fun onCreate(db: SQLiteDatabase?) {
        createDB(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DELETE + TABLE_NAME)
        createDB(db)
    }

    private fun createDB(db: SQLiteDatabase?) {
        db?.execSQL(CREATE + TABLE_NAME + START  + "date" + INT + S + "name" + TXT  + S + "data" + TXT + END)
    }

    private fun createNote(c: Cursor) : Note {
        val n = Note()
        n.setID(c.getInt(0))
        n.setDate(c.getLong(1))
        n.setTitle(c.getString(2))
        n.setText(c.getString(3))
        return n
    }

    public fun getNotes() : ArrayList<Note> {
        val list = ArrayList<Note>()
        val c = getReadableDatabase().rawQuery(SEL + TABLE_NAME, null) as Cursor
        c.moveToFirst()
        while (!c.isAfterLast()) {
            list.add(createNote(c))
            c.moveToNext()
        }
        c.close()
        return list
    }

    public fun getNote(id: Int) : Note {
        val c = getReadableDatabase().rawQuery(SEL + TABLE_NAME + WHERE + "_id=" + id, null) as Cursor
        c.moveToFirst()
        val n = createNote(c)
        c.close()
        return n
    }

    public fun addNote(n: Note) {
        val db = getWritableDatabase()
        val cv = ContentValues()
        cv.put("name", n.title)
        cv.put("data", n.date)
        cv.put("date", System.currentTimeMillis())
        db.insert(TABLE_NAME, null, cv)
    }

    public fun updateNote(n: Note) {
        val db = getWritableDatabase()
        val cv = ContentValues()
        cv.put("name", n.title);
        cv.put("data", n.date);
        db.update(TABLE_NAME, cv, null, null);
    }

    public fun removeNotes(list: ArrayList<Note>) {
        val db = getWritableDatabase()
        val sb = StringBuilder()
        sb.append("_id IN (")
        for(n in list)
            sb.append(n.id).append(",")
        if (sb.isNotEmpty())
            sb.setLength(sb.length - 1)
        sb.append(");")
        db.delete(TABLE_NAME, sb.toString(), null)
        db.close()
    }

    public fun removeNote(n: Note) {
        val db = getWritableDatabase()
        db.delete(TABLE_NAME, "_id=" + n.id, null)
        db.close()
    }

    public fun removeNote(id: Int) {
        val db = getWritableDatabase()
        db.delete(TABLE_NAME, "_id=" + id, null)
        db.close()
    }
}