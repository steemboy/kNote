package arca.knote.di

import arca.knote.mvp.model.NoteHelper
import dagger.Module
import dagger.Provides

@Module
class NoteHelperModule {
    @Provides
    fun provideNoteHelper(): NoteHelper = NoteHelper()
}