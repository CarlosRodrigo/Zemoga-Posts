package com.zemoga.posts.presentation.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.zemoga.posts.R
import com.zemoga.posts.core.State
import com.zemoga.posts.data.entities.model.Post
import com.zemoga.posts.databinding.FragmentPostsBinding
import com.zemoga.posts.presentation.adapter.PostItemTouchHelperCallback
import com.zemoga.posts.presentation.adapter.PostListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostsFragment : Fragment() {

    private val viewModel: PostsViewModel by viewModel()
    private val binding: FragmentPostsBinding by lazy {
        FragmentPostsBinding.inflate(layoutInflater)
    }
    private val adapter by lazy {
        PostListAdapter(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initBinding()
        initOptionsMenu()
        initRecyclerView()
        observePosts()
        observeSnackBar()
        return binding.root
    }

    private fun initBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }


    private fun initOptionsMenu() {
        with(binding.postsToolbar) {
            menu.clear()
            inflateMenu(R.menu.options_menu)

            menu.findItem(R.id.action_get_posts).setOnMenuItemClickListener {
                viewModel.fetchPosts()
                true
            }
            menu.findItem(R.id.action_remove_posts).setOnMenuItemClickListener {
                viewModel.removeNotFavoritePosts()
                true
            }
        }
    }

    private fun initRecyclerView() {
        adapter.onItemClickListener = { post ->
            navigateToPostDetail(post)
        }
        adapter.onFavoriteClickListener = { post ->
            favoriteItem(post)
        }
        binding.postsRv.adapter = adapter
        binding.postsRv.layoutManager = LinearLayoutManager(context)

        val itemTouchHelper = ItemTouchHelper(PostItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.postsRv)
    }

    private fun observePosts() {
        viewModel.posts.observe(viewLifecycleOwner) { state ->
            when (state) {
                State.Loading -> {
                    viewModel.showProgressBar()
                }
                is State.Error -> {
                    viewModel.hideProgressBar()
                }
                is State.Success -> {
                    viewModel.hideProgressBar()
                    adapter.updatePosts(state.result.toMutableList())
                }
            }
        }
    }

    private fun observeSnackBar() {
        viewModel.snackBar.observe(viewLifecycleOwner) {
            it?.let { error ->
                Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG).show()
                viewModel.clearSnackBar()
            }
        }
    }

    private fun navigateToPostDetail(post: Post) {
        val action = PostsFragmentDirections.actionPostsFragmentToPostDetailsFragment(post)
        findNavController().navigate(action)
    }

    private fun favoriteItem(post: Post) {
        viewModel.addPostToFavorites(post)
    }
}