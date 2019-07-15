package arca.knote

import android.app.Application
import arca.knote.classes.DBHelper

class NoteApplication : Application() {

    companion object {
        lateinit var instance: NoteApplication
            private set
    }

    lateinit var dbHelper: DBHelper

    override fun onCreate() {
        super.onCreate()

        instance = this
        dbHelper = DBHelper()
    }
}
