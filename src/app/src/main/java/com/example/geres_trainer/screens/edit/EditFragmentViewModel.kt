package com.example.geres_trainer.screens.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geres_trainer.database.translation.Translation
import com.example.geres_trainer.database.translation.TranslationDBDao
import kotlinx.coroutines.*

class EditFragmentViewModel (
    translationKey: Long = 0L,
    dataSource: TranslationDBDao
) : ViewModel() {


    val database = dataSource

    private val viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private val translation = MediatorLiveData<Translation>()

    fun getTranslation() = translation

    /**
     * Adds the translation from the database as a Source to the property of the class
     */
    init {
        translation.addSource(database.getTranslationByKey(translationKey), translation::setValue)
    }

    private val _updateIsClicked = MutableLiveData<Boolean>()
    val updateIsClicked : LiveData<Boolean>
        get() = _updateIsClicked

    private var _showSnackBarEventSuccess = MutableLiveData<Boolean>()
    val showSnackbarEventSuccess : LiveData<Boolean>
        get() = _showSnackBarEventSuccess


    /**
     * Updates the translation with the values provided by the user.
     * @param gerWord the german translation.
     * @param esWord the spanish translation.
     * @param info the info to the translation.
     */
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