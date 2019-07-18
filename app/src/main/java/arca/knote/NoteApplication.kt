package arca.knote

import android.app.Application
import android.os.Handler
import arca.knote.di.DaggerAppComponent
import io.realm.Realm

class NoteApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        instance = this
        hndler = Handler()
        appComponent = DaggerAppComponent.builder().build()
    }
}
