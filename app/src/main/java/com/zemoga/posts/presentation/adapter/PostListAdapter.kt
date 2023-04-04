package com.zemoga.posts.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zemoga.posts.R
import com.zemoga.posts.data.entities.model.Post
import com.zemoga.posts.databinding.ItemPostBinding
import com.zemoga.posts.presentation.ui.home.PostsViewModel

class PostListAdapter(
    private val viewModel: PostsViewModel,
    private val posts: MutableList<Post> = mutableListOf(),
    var onItemClickListener: (post: Post) -> Unit = {},
    var onFavoriteClickListener: (post: Post) -> Unit = {}
) :
    RecyclerView.Adapter<PostListAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder =
        PostViewHolder.from(parent)

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = posts[position]
        holder.bind(item, onItemClickListener, onFavoriteClickListener)
    }

    override fun getItemCount(): Int = posts.size

    fun updatePosts(posts: MutableList<Post>) {
        this.posts.clear()
        this.posts.addAll(posts)
        notifyDataSetChanged()
    }

    fun remove(position: Int) {
        viewModel.removePost(posts[position].id)
        posts.removeAt(position)
        notifyItemRemoved(position)
    }

    class PostViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): PostViewHolder {
                val binding: ItemPostBinding = ItemPostBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return PostViewHolder(binding)
            }
        }

        fun bind(
            item: Post,
            onItemClickListener: (post: Post) -> Unit = {},
            onFavoriteClickListener: (post: Post) -> Unit = {}
        ) {
            binding.itemTitleTv.text = item.title
            if (item.favorite == true) {
                binding.favoriteBtn.setImageResource(R.drawable.ic_favorite_yellow_48)
            } else {
                binding.favoriteBtn.setImageResource(R.drawable.ic_favorite_shadow_48)
            }
            binding.favoriteBtn.setOnClickListener {
                onFavoriteClickListener(item)
            }
            itemView.setOnClickListener {
                onItemClickListener(item)
            }
        }
    }
}