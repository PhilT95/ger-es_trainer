package com.example.geres_trainer.screens.view

import androidx.lifecycle.*
import com.example.geres_trainer.database.Translation
import com.example.geres_trainer.database.TranslationDBDao
import kotlinx.coroutines.*


class ViewFragmentViewModel (
    val database: TranslationDBDao) : ViewModel() {

    var translations = database.getAllTranslations()



    private val _navigateToEdit = MutableLiveData<Long>()
    val navigateToEdit
        get() = _navigateToEdit

    fun onTranslationClicked(id: Long) {
        _navigateToEdit.value = id
    }

    fun onEditNavigated() {
        _navigateToEdit.value = null
    }

     fun searchTranslations(searchString: String) : List<Translation>? {

         return translations.value!!.filter { it.wordGer.contains(searchString, true) || it.wordES.contains(searchString, true)  }



    }



}







