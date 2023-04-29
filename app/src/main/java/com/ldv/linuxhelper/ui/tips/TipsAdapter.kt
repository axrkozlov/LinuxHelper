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
package com.ldv.linuxhelper.ui.tips


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ldv.linuxhelper.R
import com.ldv.linuxhelper.databinding.ItemTipBinding
import com.ldv.linuxhelper.db.Tip

/**
 * Adapter for the task list. Has a reference to the [TasksViewModel] to send actions back to it.
 */
class TipsAdapter(private val viewModel: TipsViewModel) :
    ListAdapter<Tip, TipsAdapter.TipViewHolder>(TaskDiffCallback()) {

    override fun onBindViewHolder(holder: TipViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(viewModel, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipViewHolder {
        return TipViewHolder.from(parent)
    }

    class TipViewHolder private constructor(val binding: ItemTipBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: TipsViewModel, tip: Tip) {
//            binding.topicNumber.text=tip.number.toString()

            binding.title.text=tip.title
            binding.subtitle.text=tip.subtitle
            binding.root.setOnClickListener {
                viewModel.openTopic(tip)
            }

        }

        companion object {
            fun from(parent: ViewGroup): TipViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemTipBinding.inflate(layoutInflater, parent, false)

                return TipViewHolder(binding)
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
class TaskDiffCallback : DiffUtil.ItemCallback<Tip>() {
    override fun areItemsTheSame(oldItem: Tip, newItem: Tip): Boolean {
        return oldItem.number == newItem.number
    }

    override fun areContentsTheSame(oldItem: Tip, newItem: Tip): Boolean {
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