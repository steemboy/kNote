package arca.knote.mvp.model

import io.realm.Realm
import io.realm.RealmResults
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class NoteHelper {
    private var realm: Realm = Realm.getDefaultInstance()

    fun getNote(id: Int): Note? = runBlocking {
        return@runBlocking async {
            realm.where(Note::class.java).equalTo("id", id).findFirst()
        }.await()
    }

    fun getAll(): RealmResults<Note> = runBlocking {
        return@runBlocking async {
            realm.where(Note::class.java).findAll()
        }.await()
    }

    fun addOrUpdate(note: Note, title: String, text: String) = runBlocking {
        realm.executeTransaction {
            if (note.id == -1) {
                note.id = realm.where(Note::class.java).findAll().size
                note.date = System.currentTimeMillis()
            }
            note.title = title
            note.text = text
            realm.copyToRealmOrUpdate(note)
        }
    }

    fun delete(note: Note) = runBlocking {
        realm.executeTransaction {
            note.deleteFromRealm()
        }
    }

    fun deleteAll() = runBlocking {
        realm.executeTransaction {
            realm.delete(Note::class.java)
        }
    }

    fun close() = realm.close()
}