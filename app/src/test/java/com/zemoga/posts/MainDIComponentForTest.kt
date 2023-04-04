package com.zemoga.posts

import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

const val BASE_URL = "https://jsonplaceholder.typicode.com/"

fun configureTestAppComponent() = startKoin {
    loadKoinModules(
        listOf(
            configureDataModuleForTest(),
            configureDomainModuleForTest(),
        )
    )
}
