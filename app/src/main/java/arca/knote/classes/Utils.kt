@file:JvmName("DateUtils")
package arca.knote.classes

import java.text.SimpleDateFormat
import java.util.*

fun formatDate(date: Long): String {
    return SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(date))
}
