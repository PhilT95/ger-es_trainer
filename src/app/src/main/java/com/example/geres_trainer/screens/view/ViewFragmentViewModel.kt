package com.example.geres_trainer.screens.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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





    val translationString = formatTranslationsForView(database.getAllTranslations().value)



    fun doneNavigating() {
        _navigateToTitleFragment.value = null
    }


}







