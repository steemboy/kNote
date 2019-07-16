package arca.knote

import android.app.Application
import io.realm.Realm

class NoteApplication : Application() {

    companion object {
        lateinit var instance: NoteApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        instance = this
    }
}
