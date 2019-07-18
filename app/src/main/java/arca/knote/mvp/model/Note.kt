package arca.knote.mvp.model

import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey

open class Note : RealmObject() {
    open var title: String = ""
    open var text: String = ""
    @PrimaryKey
    open var date: Long = Long.MIN_VALUE

    @Ignore
    open var selected: Boolean = false

    fun setTitle(t: String): Note {
        title = t
        return this
    }

    fun setText(t: String): Note {
        text = t
        return this
    }

    fun setDate(t: Long): Note {
        date = t
        return this
    }
}
