package com.example.geres_trainer.screens.title

import android.app.Application
import android.content.Context
import androidx.core.graphics.translationMatrix
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.geres_trainer.database.config.Configuration
import com.example.geres_trainer.database.config.ConfigurationDAO
import com.example.geres_trainer.database.translation.TranslationDB
import com.example.geres_trainer.database.translation.TranslationDBDao
import kotlinx.coroutines.*


/**
 * This class is responsible for calculations that happen in the background of the title fragment.
 *
 * @property database the database access interface.
 * @param application the application and its context.
 */
class TitleFragmentViewModel (
    val database: TranslationDBDao,
    private val configDB: ConfigurationDAO,
    application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job ()


    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val config = MediatorLiveData<Configuration>()

    fun getConfig() = config

    var gameLength = 0

    private var _showSnackBarEvent = MutableLiveData<Boolean>()

    val showSnackbarEvent: LiveData<Boolean>
        get() = _showSnackBarEvent


    private var _databaseReset = MutableLiveData<Boolean>()
    val databaseReset : LiveData<Boolean>
        get() = _databaseReset


    init {
        config.addSource(configDB.getConfiguration(), config::setValue)
    }



    fun updateGameLength(int : Int) {
        gameLength = int
    }


    private suspend fun onSaveGameLength(){
        withContext(Dispatchers.IO){
            val newConfig = config.value ?: return@withContext
            //val id = config.value!!.configID
            newConfig.gameLength = gameLength
            configDB.update(newConfig)
        }
    }

    fun saveGameLength(){
        uiScope.launch {
            onSaveGameLength()
        }
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