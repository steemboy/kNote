@file:JvmName("DateUtils")
package arca.knote

import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

fun formatDate(date: Long): String {
    return SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(date))
}

fun mateShortToast(text: String) = NoteApplication.hndler.post { Toast.makeText(NoteApplication.instance, text, Toast.LENGTH_SHORT).show() }
