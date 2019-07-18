package arca.knote.mvp.model

import io.realm.Realm
import io.realm.RealmResults

class NoteHelper {
    private var realm: Realm = Realm.getDefaultInstance()

    fun getNote(id: Int) : Note? {
        return realm.where(Note::class.java).equalTo("id", id).findFirst()
    }

    fun getAll() : RealmResults<Note> = realm.where(Note::class.java).findAll()

    fun addOrUpdate(note: Note, title: String, text: String) {
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

    fun delete(note: Note) {
        realm.executeTransaction {
            note.deleteFromRealm()
        }
    }

    fun deleteAll() {
        realm.executeTransaction {
            realm.delete(Note::class.java)
        }
    }

    fun close() =  realm.close()
}