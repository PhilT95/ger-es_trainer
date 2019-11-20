package com.example.geres_trainer.screens.view

import android.app.Application
import androidx.lifecycle.*
import com.example.geres_trainer.database.Translation
import com.example.geres_trainer.database.TranslationDBDao
import com.example.geres_trainer.formatTranslationsForView
import kotlinx.coroutines.*

class ViewFragmentViewModel (
    val database: TranslationDBDao) : ViewModel() {


    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    private val _navigateToTitleFragment = MutableLiveData<Boolean?>()

    val navigateToTitleFragment: LiveData<Boolean?>
        get() = _navigateToTitleFragment






    private val translations = database.getAllTranslations()

    val translationsString = Transformations.map(translations) {translations ->
        (formatTranslationsForView(translations))
    }








    fun doneNavigating() {
        _navigateToTitleFragment.value = null
    }


}







