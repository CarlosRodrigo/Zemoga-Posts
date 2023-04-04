package com.zemoga.posts.presentation.ui.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.zemoga.posts.R
import com.zemoga.posts.core.State
import com.zemoga.posts.databinding.FragmentPostDetailsBinding
import com.zemoga.posts.presentation.adapter.PostCommentsListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostDetailsFragment : Fragment() {

    private val args: PostDetailsFragmentArgs by navArgs()
    private val viewModel: PostDetailsViewModel by viewModel()
    private val binding: FragmentPostDetailsBinding by lazy {
        FragmentPostDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initBinding()
        initRecyclerView()
        fetchComments()
        observeSnackBar()
        return binding.root
    }

    private fun initBinding() {
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun initRecyclerView() {
        viewModel.comments.observe(viewLifecycleOwner) { state ->
            when (state) {
                State.Loading -> TODO()
                is State.Error -> TODO()
                is State.Success -> {
                    val adapter = PostCommentsListAdapter(state.result)
                    binding.postTitleDetail.text = args.post.title
                    binding.postDescriptionDetail.text = args.post.body
                    binding.postCommentsRv.adapter = adapter
                    binding.postCommentsRv.layoutManager = LinearLayoutManager(context)
                }
            }
        }
    }

    private fun fetchComments() {
        val postId = args.post.id
        viewModel.fetchPostComments(postId)
    }

    private fun observeSnackBar() {
        viewModel.snackBar.observe(viewLifecycleOwner) {
            it?.let { error ->
                Snackbar.make(binding.root, error, Snackbar.LENGTH_LONG).show()
                viewModel.clearSnackBar()
            }
        }
    }
}