package com.heyproject.githubapps.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heyproject.githubapps.databinding.ItemUserBinding
import com.heyproject.githubapps.domain.model.User


/**
Written by Yayan Rahmat Wijaya on 11/14/2022 08:54
Github : https://github.com/yayanrw
 **/

class FollowAdapter : ListAdapter<User, FollowAdapter.FollowViewHolder>(DiffCallback) {
    var onItemClick: ((User) -> Unit)? = null

    inner class FollowViewHolder(private var binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userData: User?) {
            binding.apply {
                user = userData
                executePendingBindings()
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(getItem(bindingAdapterPosition)!!)
            }
        }
    }

    override fun onBindViewHolder(holder: FollowAdapter.FollowViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): FollowAdapter.FollowViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowViewHolder(binding)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id == newItem.id
        }
    }
}