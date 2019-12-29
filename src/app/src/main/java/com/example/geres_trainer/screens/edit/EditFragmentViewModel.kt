package com.example.geres_trainer.screens.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geres_trainer.database.Translation
import com.example.geres_trainer.database.TranslationDBDao
import kotlinx.coroutines.*

class EditFragmentViewModel (
    private val translationKey: Long = 0L,
    dataSource: TranslationDBDao) : ViewModel() {


    val database = dataSource

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val translation = MediatorLiveData<Translation>()

    fun getTranslation() = translation

    init {
        translation.addSource(database.getTranslationByKey(translationKey), translation::setValue)
    }

    private val _updateIsClicked = MutableLiveData<Boolean>()
    val updateIsClicked : LiveData<Boolean>
        get() = _updateIsClicked

    private var _showSnackBarEventSuccess = MutableLiveData<Boolean>()
    val showSnackbarEventSuccess : LiveData<Boolean>
        get() = _showSnackBarEventSuccess

    private var _showSnackBarEventFail = MutableLiveData<Boolean>()
    val showSnackbarEventFail : LiveData<Boolean>
        get() = _showSnackBarEventFail



    fun updateClicked(gerWord : String, esWord: String, info : String) {
        _updateIsClicked.value = true
        if(gerWord == translation.value!!.wordGer && esWord == translation.value!!.wordES && info == translation.value!!.info) {

        }
        else{
            uiScope.launch {
                withContext(Dispatchers.IO) {
                    val newTranslation = translation.value ?: return@withContext
                    newTranslation.wordGer = gerWord
                    newTranslation.wordES = esWord
                    newTranslation.info = info
                    database.update(newTranslation)


                }
            }
        }
        _showSnackBarEventSuccess.value = true


        _updateIsClicked.value = false


    }



    fun doneShowingSnackbarSuccess () {
        _showSnackBarEventSuccess.value = false
    }


}