package arca.knote.mvp.model

import io.realm.Realm
import io.realm.RealmResults
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class NoteHelper {
    private var realm: Realm = Realm.getDefaultInstance()

    fun getNote(id: Long): Note? = runBlocking {
        return@runBlocking async {
            realm.where(Note::class.java).equalTo("date", id).findFirst()
        }.await()
    }

    fun getAll(): RealmResults<Note> = runBlocking {
        return@runBlocking async {
            realm.where(Note::class.java).findAll()
        }.await()
    }

    fun addOrUpdate(note: Note, title: String, text: String) = runBlocking {
        realm.executeTransaction {
            if (note.date == Long.MIN_VALUE)
                note.date = System.currentTimeMillis()
            note.title = title
            note.text = text
            realm.copyToRealmOrUpdate(note)
        }
    }

    fun delete(notes: ArrayList<Note>) = runBlocking{
        realm.executeTransaction {
            for(n in notes)
                n.deleteFromRealm()
        }
    }

    fun close() = realm.close()
}