package com.example.github_project.presentation.trending.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.github_project.R
import com.example.github_project.databinding.ItemRepoBinding
import com.example.github_project.domain.model.Repo

class RepoAdapter : PagingDataAdapter<Repo, RepoAdapter.VH>(Diff) {

    object Diff : DiffUtil.ItemCallback<Repo>() {
        override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem.name == newItem.name && oldItem.ownerName == newItem.ownerName
        }

        override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
            return oldItem == newItem
        }
    }

    class VH(val binding: ItemRepoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemRepoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = getItem(position) ?: return
        holder.binding.repoName.text = item.name
        holder.binding.ownerName.text = holder.itemView.context.getString(R.string.by_owner, item.ownerName)
        holder.binding.description.text = item.description
        holder.binding.stars.text = holder.itemView.context.getString(R.string.stars_format, item.stars)
        holder.binding.avatar.load(item.ownerAvatarUrl)
    }
}