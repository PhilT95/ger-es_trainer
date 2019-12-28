package com.example.geres_trainer.screens.view

import androidx.lifecycle.*
import com.example.geres_trainer.database.TranslationDBDao
import com.example.geres_trainer.util.formatTranslationsForView

class ViewFragmentViewModel (
    val database: TranslationDBDao) : ViewModel() {

    val translations = database.getAllTranslations()

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







