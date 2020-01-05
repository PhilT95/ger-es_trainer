package com.example.geres_trainer.screens.end

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.geres_trainer.database.translation.TranslationDBDao


class EndFragmentViewModel (
    val database : TranslationDBDao,
    keys: List<Long>,
    application: Application): AndroidViewModel (application) {

    val translations = database.getTranslationsByKeys(keys.toLongArray())

    private val _navigateToEdit = MutableLiveData<Long>()
    val navigateToEdit
        get() = _navigateToEdit

    fun onTranslationClicked(id: Long) {
        _navigateToEdit.value = id
    }

    fun onEditNavigated() {
        _navigateToEdit.value = null
    }

}
