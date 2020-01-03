package com.example.geres_trainer.util.adapter

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.geres_trainer.database.Translation

/**
 * These functions are bound to the corresponding layout element of the RecyclerView.
 * They define which part of the translation should be displayed where in the layout.
 */


@BindingAdapter("translationGermanString")
fun TextView.setTranslationGermanString(item: Translation?) {
    item?.let {
        text = item.wordGer
    }
}

@BindingAdapter("translationSpanishString")
fun TextView.setTranslationSpanishString(item: Translation?) {
    item?.let {
        text = item.wordES
    }
}

@BindingAdapter("translationInfoString")
fun TextView.setTranslationInfoString(item: Translation?) {
    item?.let {
        text = item.info
    }
}