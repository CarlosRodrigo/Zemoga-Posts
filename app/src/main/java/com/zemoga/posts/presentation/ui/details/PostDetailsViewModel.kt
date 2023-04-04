package com.zemoga.posts.presentation.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zemoga.posts.core.State
import com.zemoga.posts.data.entities.model.Comment
import com.zemoga.posts.domain.usecases.GetPostCommentsUseCase
import kotlinx.coroutines.launch

class PostDetailsViewModel(private val getPostCommentsUseCase: GetPostCommentsUseCase) : ViewModel() {

    private val _comments = MutableLiveData<State<List<Comment>>>()
    val comments: LiveData<State<List<Comment>>>
        get() = _comments

    private val _snackBar = MutableLiveData<String?>(null)
    val snackBar: LiveData<String?>
        get() = _snackBar

    fun fetchPostComments(postId: Int) {
        viewModelScope.launch {
            getPostCommentsUseCase(postId)
                .collect {
                    it.data?.let { comments ->
                        _comments.postValue(State.Success(comments))
                    }
                    it.error?.let { error ->
                        _snackBar.value = error.message
                    }
                }
        }
    }

    fun clearSnackBar() {
        _snackBar.value = null
    }
}