package arca.knote

import android.app.Application
import android.os.Handler
import io.realm.Realm

class NoteApplication : Application() {

    companion object {
        lateinit var instance: NoteApplication
        lateinit var hndler: Handler
    }

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        instance = this
        hndler = Handler()
    }
}
