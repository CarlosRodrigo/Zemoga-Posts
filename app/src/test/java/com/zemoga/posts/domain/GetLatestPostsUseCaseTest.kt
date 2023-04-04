package com.zemoga.posts.domain

import com.zemoga.posts.configureTestAppComponent
import com.zemoga.posts.core.Resource
import com.zemoga.posts.data.entities.model.Post
import com.zemoga.posts.domain.usecases.GetLatestPostsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GetLatestPostsUseCaseTest : KoinTest {

    val getLatestPostsUseCase: GetLatestPostsUseCase by inject()

    companion object {

        @BeforeClass
        @JvmStatic
        fun setup() {
            configureTestAppComponent()
        }

        /**
         * Stop Koin after each test to prevent errors
         */
        @AfterClass
        fun tearDown() {
            stopKoin()
        }
    }


    @Test
    fun should_ReturnNotNull_WhenConnectingToRepository() {
        runBlocking {
            val result = getLatestPostsUseCase()

            assertNotNull(result)
            result.collect {
                assertTrue(it is Resource<List<Post>>)
                assertTrue(it.data!!.isNotEmpty())
            }
        }
    }
}