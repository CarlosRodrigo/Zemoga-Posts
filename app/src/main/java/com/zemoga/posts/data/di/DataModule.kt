package com.zemoga.posts.data.di

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.zemoga.posts.data.database.PostDatabase
import com.zemoga.posts.data.remote.PostsAPI
import com.zemoga.posts.data.repository.PostsRepository
import com.zemoga.posts.data.repository.PostsRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * This class handles the Data dependencies
 */
object DataModule {

    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"
    private const val OK_HTTP = "OK_HTTP"

    fun load() {
        loadKoinModules(
            listOf(
                postsModule(),
                networkModule(),
                daoModule()
            )
        )
    }

    private fun postsModule(): Module {
        return module {
            single<PostsRepository> {
                PostsRepositoryImpl(get(), get())
            }
        }
    }

    private fun daoModule(): Module {
        return module {
            single {
                PostDatabase.getInstance(androidContext()).dao
            }
        }
    }

    private fun networkModule(): Module {
        return module {
            single<PostsAPI> { createAPI(get(), get()) }

            single { Moshi.Builder().add((KotlinJsonAdapterFactory())).build() }

            single {
                val interceptor = HttpLoggingInterceptor {
                    Log.d(OK_HTTP, it)
                }
                interceptor.level = HttpLoggingInterceptor.Level.BODY
                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            }
        }
    }

    private inline fun <reified T> createAPI(
        factory: Moshi,
        client: OkHttpClient
    ): T {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(factory))
            .client(client)
            .build()
            .create(T::class.java)
    }
}