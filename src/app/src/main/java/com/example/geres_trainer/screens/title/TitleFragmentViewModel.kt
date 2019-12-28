package com.example.geres_trainer.screens.title

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.geres_trainer.database.Translation
import com.example.geres_trainer.database.TranslationDB
import com.example.geres_trainer.database.TranslationDBDao
import com.example.geres_trainer.util.populateDatabase
import kotlinx.coroutines.*

class TitleFragmentViewModel (
    val database: TranslationDBDao,
    application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job ()


    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _showSnackBarEvent = MutableLiveData<Boolean>()

    val showSnackbarEvent: LiveData<Boolean>
        get() = _showSnackBarEvent


    private var _searchedTranslation = MutableLiveData<String>()

    val searchedTranslation : LiveData<String>
        get() = _searchedTranslation

    private var _databaseReset = MutableLiveData<Boolean>()
    val databaseReset : LiveData<Boolean>
        get() = _databaseReset





    fun doneShowingSnackbar () {
        _showSnackBarEvent.value = false
    }





    private suspend fun getTranslationFromDatabase(string : String) : Translation? {
        return withContext(Dispatchers.IO) {
            var translation = database.getTranslatioByGer(string)
            translation
        }
    }

    private fun clear() {
        _databaseReset.value = true
    }



    fun deleteDatabase(context: Context) {
        TranslationDB.destroyInstance()
        context.deleteDatabase("translation_database")
        _databaseReset.value = false
    }







    fun onEdit() {
        uiScope.launch {

            val translation = getTranslationFromDatabase("Frage")
            if (translation != null){
                _searchedTranslation.value = translation.wordES
            }
            else {
                _searchedTranslation.value = "No Word found!"
            }

        }

        _showSnackBarEvent.value = true
    }

    fun onClear() {
        uiScope.launch {
            clear()


        }

        _showSnackBarEvent.value = true
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }



}