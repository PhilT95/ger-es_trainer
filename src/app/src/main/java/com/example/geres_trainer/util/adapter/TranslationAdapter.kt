package com.example.geres_trainer.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.geres_trainer.database.Translation
import com.example.geres_trainer.databinding.ListItemTranslationBinding


/**
 * This class is used to adapt translations to a RecyclerView.
 * It uses the other classes in this file to calculate differences for updating the list when changes occur and a ClickHandler.
 */
class TranslationAdapter(val clickListener: TranslationListener) : ListAdapter<Translation,
        TranslationAdapter.ViewHolder>(
    TranslationDiffCallback()
) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder.from(
            parent
        )
    }


    /**
     * Implements a ViewHolder within the TranslationAdapterClass to connect the Adapter and all its functions to the layout xml.
     */
    class ViewHolder private constructor(val binding: ListItemTranslationBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: TranslationListener, item: Translation) {
            binding.translation = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemTranslationBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(
                    binding
                )
            }
        }
    }
}

/**
 * This class is used to calculate the differences between two elements to update the RecyclerView easier.
 */
class TranslationDiffCallback : DiffUtil.ItemCallback<Translation>() {
    override fun areItemsTheSame(oldItem: Translation, newItem: Translation): Boolean {
        return oldItem.translationID == newItem.translationID
    }

    override fun areContentsTheSame(oldItem: Translation, newItem: Translation): Boolean {
        return oldItem == newItem
    }
}

/**
 * Implements the Listener for a click on a translation in the RecyclerView
 */
class TranslationListener(val clickListener: (translationID: Long) -> Unit) {
    fun onClick(translation: Translation) = clickListener(translation.translationID)
}

