/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ldv.linuxhelper.ui.home


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ldv.linuxhelper.R
import com.ldv.linuxhelper.databinding.ItemTopicBinding
import com.ldv.linuxhelper.db.Topic

/**
 * Adapter for the task list. Has a reference to the [TasksViewModel] to send actions back to it.
 */
class TopicsAdapter(private val viewModel: HomeViewModel) :
    ListAdapter<Topic, TopicsAdapter.TopicViewHolder>(TaskDiffCallback()) {

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        return TopicViewHolder.from(parent)
    }

    class TopicViewHolder private constructor(val binding: ItemTopicBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: HomeViewModel, topic: Topic) {
            binding.topicNumber.text=topic.number.toString()

            binding.title.text=topic.title
            binding.subtitle.text=topic.subtitle
            var hashtag =StringBuilder()
            topic.topicParts.forEach {
                hashtag.append("#${it.command} ")
            }
            binding.subtitle.text=hashtag.toString()

            binding.root.setOnClickListener {
                viewModel.openTopic(topic)
            }

            val isBookmarkedResource = if (topic.isBookmarked) R.drawable.bookmark_on else R.drawable.bookmark_off
            binding.bookmark.setImageResource(isBookmarkedResource)

            binding.bookmark.setOnClickListener {
                topic.isBookmarked=!topic.isBookmarked
                viewModel.updateTopic(topic)
            }
            binding.share.setOnClickListener {
                viewModel.shareTopic(topic)
            }

            val likeCountVisibility = if (topic.likeCount>0)View.VISIBLE else View.GONE
            binding.likeCount.visibility = likeCountVisibility
            binding.likeCount.text =topic.likeCount.toString()
            binding.like.setOnClickListener {
                topic.likeCount++
                viewModel.updateTopic(topic)
            }
        }

        companion object {
            fun from(parent: ViewGroup): TopicViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTopicBinding.inflate(layoutInflater, parent, false)

                return TopicViewHolder(binding)
            }
        }
    }
}

/**
 * Callback for calculating the diff between two non-null items in a list.
 *
 * Used by ListAdapter to calculate the minimum number of changes between and old list and a new
 * list that's been passed to `submitList`.
 */
class TaskDiffCallback : DiffUtil.ItemCallback<Topic>() {
    override fun areItemsTheSame(oldItem: Topic, newItem: Topic): Boolean {
        return oldItem.number == newItem.number
    }

    override fun areContentsTheSame(oldItem: Topic, newItem: Topic): Boolean {
        return oldItem == newItem
    }
}

//
//class HoursAdapter(private val hoursList: List<HoursItem>)
//    :RecyclerView.Adapter<HoursAdapter.HoursViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HoursViewHolder {
//        val binding = HoursListItemsBinding
//            .inflate(LayoutInflater.from(parent.context), parent, false)
//        return HoursViewHolder(binding)
//    }
//
//    override fun getItemCount() = hoursList.size
//
//    override fun onBindViewHolder(holder: HoursViewHolder, position: Int) {
//        with(holder){
//            with(hoursList[position]) {
//                binding.topLearnerName.text = name
//                val hours = "$hours learning hours, $country"
//                binding.topLearnerTime.text = hours
//                GlideApp.with(holder.itemView.context)
//                    .load(badgeUrl)
//                    .into(binding.topLearnerImage)
//
//                holder.itemView.setOnClickListener {
//                    Toast.makeText(holder.itemView.context, hours,
//                        Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//
//    inner class HoursViewHolder(val binding: HoursListItemsBinding)
//        :RecyclerView.ViewHolder(binding.root)
//
//}