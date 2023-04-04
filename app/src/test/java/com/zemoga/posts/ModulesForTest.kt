package com.zemoga.posts

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zemoga.posts.data.remote.PostsAPI
import com.zemoga.posts.data.repository.PostsRepository
import com.zemoga.posts.data.repository.PostsRepositoryImpl
import com.zemoga.posts.domain.usecases.GetLatestPostsUseCase
import com.zemoga.posts.mock.PostsRepositoryMock
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun configureDomainModuleForTest() = module {
    factory { GetLatestPostsUseCase(get()) }
}

fun configureDataModuleForTest() = module {
    single<PostsRepository> { PostsRepositoryMock() }
}