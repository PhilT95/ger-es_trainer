package com.example.geres_trainer.screens.end

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.geres_trainer.database.Translation
import com.example.geres_trainer.databinding.ListItemTranslationBinding

class TranslationAdapter : ListAdapter<Translation,
        TranslationAdapter.ViewHolder>(TranslationDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder.from(parent)
    }


    class ViewHolder private constructor(val binding: ListItemTranslationBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Translation) {
            binding.translation = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemTranslationBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
    }




    }
}

class TranslationDiffCallback : DiffUtil.ItemCallback<Translation>() {
    override fun areItemsTheSame(oldItem: Translation, newItem: Translation): Boolean {
        return oldItem.translationID == newItem.translationID
    }

    override fun areContentsTheSame(oldItem: Translation, newItem: Translation): Boolean {
        return oldItem == newItem
    }
}

