package com.zemoga.posts.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zemoga.posts.data.entities.model.Comment
import com.zemoga.posts.databinding.ItemPostCommentsBinding

class PostCommentsListAdapter(private val comments: List<Comment>) :
    RecyclerView.Adapter<PostCommentsListAdapter.PostCommentsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostCommentsViewHolder =
        PostCommentsViewHolder.from(parent)

    override fun onBindViewHolder(holder: PostCommentsViewHolder, position: Int) {
        val item = comments[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = comments.size

    class PostCommentsViewHolder(private val binding: ItemPostCommentsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): PostCommentsViewHolder {
                val binding: ItemPostCommentsBinding = ItemPostCommentsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return PostCommentsViewHolder(binding)
            }
        }

        fun bind(item: Comment) {
            binding.itemCommentTitleTv.text = item.body
        }
    }
}