package arca.knote.mvp.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Note : RealmObject() {
    open var title: String = ""
    open var text: String = ""
    open var date: Long = 0
    @PrimaryKey
    open var id: Int = -1

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

    fun setID(t: Int): Note {
        id = t
        return this
    }
}
