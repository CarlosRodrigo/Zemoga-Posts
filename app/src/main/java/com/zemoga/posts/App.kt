package com.zemoga.posts

import android.app.Application
import com.zemoga.posts.data.di.DataModule
import com.zemoga.posts.domain.di.DomainModule
import com.zemoga.posts.presentation.di.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Entrypoint class for the Zemoga Posts App
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
        }

        PresentationModule.load()
        DataModule.load()
        DomainModule.load()
    }
}