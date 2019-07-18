package arca.knote

import android.app.Application
import android.os.Handler
import arca.knote.di.DaggerAppComponent
import io.realm.Realm
import io.realm.RealmConfiguration



class NoteApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Realm.init(this)

        val config = RealmConfiguration.Builder()
            .schemaVersion(1)
            .deleteRealmIfMigrationNeeded()
            .build()

        Realm.setDefaultConfiguration(config)

        instance = this
        hndler = Handler()
        appComponent = DaggerAppComponent.builder().build()
    }
}
