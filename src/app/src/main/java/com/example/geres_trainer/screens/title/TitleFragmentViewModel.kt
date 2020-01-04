package com.example.geres_trainer.screens.title

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.geres_trainer.database.TranslationDB
import com.example.geres_trainer.database.TranslationDBDao
import kotlinx.coroutines.*


/**
 * This class is responsible for calculations that happen in the background of the title fragment.
 *
 * @property database the database access interface.
 * @param application the application and its context.
 */
class TitleFragmentViewModel (
    val database: TranslationDBDao,
    application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job ()


    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _showSnackBarEvent = MutableLiveData<Boolean>()

    val showSnackbarEvent: LiveData<Boolean>
        get() = _showSnackBarEvent


    private var _databaseReset = MutableLiveData<Boolean>()
    val databaseReset : LiveData<Boolean>
        get() = _databaseReset

    private var _gameLengthSelectorText = MutableLiveData<String>()
    val gameLengthSelectorText : LiveData<String>
        get() = _gameLengthSelectorText

    fun updateText(str : String) {
        _gameLengthSelectorText.value = "Current game length: $str"
    }






    fun doneShowingSnackbar () {
        _showSnackBarEvent.value = false
    }

    private fun clear() {
        _databaseReset.value = true
    }


    /**
     * This function deletes the current database so it can be re-created from scratch.
     * First it deletes the currently loaded instance of the database from the context, then the database gets deleted.
     * @param context the context of the application
     */
    fun deleteDatabase(context: Context) {
        TranslationDB.destroyInstance()
        context.deleteDatabase("translation_database")
        _databaseReset.value = false
    }

    fun onClear() {
        uiScope.launch {
            clear()


        }

        _showSnackBarEvent.value = true
    }

    /**
     * Clears all current Coroutine jobs in the background before closing-
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }



}