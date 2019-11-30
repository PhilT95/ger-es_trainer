package com.example.geres_trainer.screens.add

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.geres_trainer.database.Translation
import com.example.geres_trainer.database.TranslationDBDao
import kotlinx.coroutines.*

class AddFragmentViewModel (
    val database: TranslationDBDao,
    application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)





    private var _showSnackBarEventSuccess = MutableLiveData<Boolean>()
    val showSnackbarEventSuccess : LiveData<Boolean>
        get() = _showSnackBarEventSuccess

    private var _showSnackBarEventFail = MutableLiveData<Boolean>()
    val showSnackbarEventFail : LiveData<Boolean>
        get() = _showSnackBarEventFail

    private var _canAddTranslation = MutableLiveData<Boolean>()
    val canAddTranslation : LiveData<Boolean>
        get() = _canAddTranslation

    private var _showSnackBarEventIllegalSymbol = MutableLiveData<Boolean>()
    val showSnackBarEventIllegalSymbol : LiveData<Boolean>
        get() = _showSnackBarEventIllegalSymbol





    private suspend fun add(gerWord : String, esWord : String, info : String) {
        var didWork = false
        var containsIllegalSymbol = false
        withContext(Dispatchers.IO) {
            val newTranslation = Translation()
            newTranslation.wordGer = gerWord
            newTranslation.wordES = esWord
            newTranslation.info = info





            try {
                if(!info.contains(";")){
                    database.insert(newTranslation)
                    didWork = true
                }
                else {
                    containsIllegalSymbol = true
                    didWork = false

                }
            }
            catch (e: SQLiteConstraintException){
                didWork = false
            }

        }

        if (didWork) {
            _showSnackBarEventSuccess.value = true
        }
        else if (containsIllegalSymbol){
            _showSnackBarEventIllegalSymbol.value = true
        }
        else {
            _showSnackBarEventFail.value = true
        }

        _canAddTranslation.value = false
    }


    fun onAdd(gerWord : String, esWord : String, info : String) {
        uiScope.launch {
            add(gerWord, esWord, info)
        }


    }

    fun doneShowingSnackbarSuccess () {
        _showSnackBarEventSuccess.value = false
    }

    fun doneShowingSnackbarFail () {
        _showSnackBarEventFail.value = false
    }

    fun doneShowingSnackbarIllegalSymbol () {
        _showSnackBarEventIllegalSymbol.value = false
    }

    fun checkState (gerWord : String, esWord : String) {
      if(gerWord != "" && esWord != "") {
          _canAddTranslation.value = true
      }
    }


}
