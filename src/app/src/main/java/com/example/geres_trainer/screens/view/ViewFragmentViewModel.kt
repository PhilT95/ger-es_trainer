package com.example.geres_trainer.screens.view

import androidx.lifecycle.*
import com.example.geres_trainer.database.TranslationDBDao
import com.example.geres_trainer.util.formatTranslationsForView

class ViewFragmentViewModel (
    val database: TranslationDBDao) : ViewModel() {


    //private val viewModelJob = Job()

    //private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    //private val _navigateToTitleFragment = MutableLiveData<Boolean?>()







    private val translations = database.getAllTranslations()

    val lists = Transformations.map(translations) { translations ->
        (formatTranslationsForView(translations))
    }

    val gerList = Transformations.map(lists) {lists ->
        lists.get(0)
    }

    val esList = Transformations.map(lists) {lists ->
        lists.get(1)
    }

    val infoList = Transformations.map(lists) {lists ->
        lists.get(2)
    }












}







