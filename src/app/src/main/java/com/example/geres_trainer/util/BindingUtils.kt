package com.example.geres_trainer.util

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.geres_trainer.database.Translation




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