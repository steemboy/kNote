package arca.knote.di

import arca.knote.mvp.presenters.MainPresenter
import arca.knote.mvp.presenters.NotePresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NoteHelperModule::class])
interface AppComponent {
    fun inject(mPresenter: MainPresenter)
    fun inject(nPresenter: NotePresenter)
}
