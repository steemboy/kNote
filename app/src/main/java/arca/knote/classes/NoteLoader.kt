package arca.knote.classes

import android.os.AsyncTask
import arca.knote.NoteApplication
import arca.knote.model.Note

class NoteLoader(l: LoaderInterface) : AsyncTask<Void, Void, ArrayList<Note>>() {
    private var lois: LoaderInterface = l

    override fun doInBackground(vararg params: Void?): ArrayList<Note> {
        return NoteApplication.instance.dbHelper.getNotes()
    }

    override fun onPostExecute(result: ArrayList<Note>?) {
        super.onPostExecute(result)
        lois.onLoad(result)
    }
}