package com.zemoga.posts.domain.di

import com.zemoga.posts.domain.usecases.*
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

object DomainModule {

    fun load() {
        loadKoinModules(useCaseModule())
    }

    private fun useCaseModule(): Module {
        return module {
            factory { GetLatestPostsUseCase(get()) }
            factory { GetPostCommentsUseCase(get()) }
            factory { FavoritePostUseCase(get()) }
            factory { RemoveNotFavoritePostsUseCase(get()) }
            factory { RemovePostUseCase(get()) }
        }
    }
}