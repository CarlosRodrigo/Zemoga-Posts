package com.zemoga.posts.presentation.di

import com.zemoga.posts.presentation.ui.details.PostDetailsViewModel
import com.zemoga.posts.presentation.ui.home.PostsViewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * This class handles the Presentation dependencies
 */
object PresentationModule {

    fun load() {
        loadKoinModules(viewModelModule())
    }

    private fun viewModelModule(): Module {
        return module {
            factory { PostsViewModel(get(), get(), get(), get()) }
            factory { PostDetailsViewModel(get()) }
        }
    }
}