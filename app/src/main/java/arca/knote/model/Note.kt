package arca.knote.model

class Note {
    var title: String = ""
    var text: String = ""
    var date: Long = 0
    var id: Int = 0

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
