package com.zemoga.posts.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zemoga.posts.core.State
import com.zemoga.posts.data.entities.model.Post
import com.zemoga.posts.domain.usecases.FavoritePostUseCase
import com.zemoga.posts.domain.usecases.GetLatestPostsUseCase
import com.zemoga.posts.domain.usecases.RemoveNotFavoritePostsUseCase
import com.zemoga.posts.domain.usecases.RemovePostUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PostsViewModel(
    private val getLatestPostsUseCase: GetLatestPostsUseCase,
    private val favoritePostUseCase: FavoritePostUseCase,
    private val removeNotFavoritePostsUseCase: RemoveNotFavoritePostsUseCase,
    private val removePostUseCase: RemovePostUseCase
) : ViewModel() {

    private val _progressBarVisibility = MutableLiveData(false)
    val progressBarVisibility: LiveData<Boolean>
        get() = _progressBarVisibility

    private val _snackBar = MutableLiveData<String?>(null)
    val snackBar: LiveData<String?>
        get() = _snackBar

    private val _posts = MutableLiveData<State<List<Post>>>()
    val posts: LiveData<State<List<Post>>>
        get() = _posts

    init {
        fetchPosts()
    }

    fun showProgressBar() {
        _progressBarVisibility.value = true
    }

    fun hideProgressBar() {
        _progressBarVisibility.value = false
    }

    fun clearSnackBar() {
        _snackBar.value = null
    }

    fun fetchPosts() {
        _posts.postValue(State.Success(emptyList<Post>().toMutableList()))
        viewModelScope.launch {
            getLatestPostsUseCase()
                .onStart {
                    _posts.postValue(State.Loading)
                }
                .catch {
                    _posts.postValue(State.Error(it))
                    _snackBar.postValue(it.message)
                }
                .collect {
                    it.data?.let { posts ->
                        _posts.postValue(State.Success(posts))
                    }
                    it.error?.let { error ->
                        _snackBar.value = error.message
                    }
                }
        }
    }

    fun addPostToFavorites(post: Post) {
        viewModelScope.launch {
            favoritePostUseCase(post)
                .collect {
                    it.data?.let { posts ->
                        _posts.postValue(State.Success(posts))
                    }
                    it.error?.let { error ->
                        _snackBar.value = error.message
                    }
                }
        }
    }

    fun removeNotFavoritePosts() {
        viewModelScope.launch {
            removeNotFavoritePostsUseCase()
                .catch {
                    _posts.postValue(State.Error(it))
                    _snackBar.postValue(it.message)
                }
                .collect {
                    it.data?.let { posts ->
                        _posts.postValue(State.Success(posts))
                    }
                    it.error?.let { error ->
                        _snackBar.value = error.message
                    }
                }
        }
    }

    fun removePost(postId: Int) {
        viewModelScope.launch {
            removePostUseCase(postId)
                .collect {
                    it.data?.let {
                        _snackBar.value = "Post deleted."
                    }
                }
        }
    }
}