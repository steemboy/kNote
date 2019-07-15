package arca.knote.classes

import arca.knote.model.Note

interface LoaderInterface {
    fun onLoad(list : ArrayList<Note>?)
}