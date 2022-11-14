package com.heyproject.githubapps.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.heyproject.githubapps.databinding.ItemUserBinding
import com.heyproject.githubapps.domain.model.UserDetail
import com.heyproject.githubapps.domain.model.toUser


/**
Written by Yayan Rahmat Wijaya on 11/14/2022 22:44
Github : https://github.com/yayanrw
 **/

class UserDetailAdapter :
    ListAdapter<UserDetail, UserDetailAdapter.UserDetailViewHolder>(DiffCallback) {
    var onItemClick: ((UserDetail) -> Unit)? = null

    inner class UserDetailViewHolder(private var binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(userData: UserDetail?) {
            binding.apply {
                user = userData?.toUser()
                executePendingBindings()
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(getItem(bindingAdapterPosition)!!)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDetailViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserDetailViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<UserDetail>() {
        override fun areItemsTheSame(oldItem: UserDetail, newItem: UserDetail): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: UserDetail, newItem: UserDetail): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
