@file:JvmName("Utils")
package arca.knote

import android.os.Handler
import android.widget.Toast
import arca.knote.di.AppComponent
import java.text.SimpleDateFormat
import java.util.*

lateinit var instance: NoteApplication
lateinit var hndler: Handler
lateinit var appComponent: AppComponent

fun formatDate(date: Long): String {
    return SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(date))
}

fun mateShortToast(text: String) = hndler.post { Toast.makeText(instance, text, Toast.LENGTH_SHORT).show() }
