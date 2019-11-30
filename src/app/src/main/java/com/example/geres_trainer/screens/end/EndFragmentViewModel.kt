package com.example.geres_trainer.screens.end

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.geres_trainer.database.Translation
import com.example.geres_trainer.database.TranslationDBDao
import com.example.geres_trainer.util.formatTranslationForEndView
import kotlinx.coroutines.*
import java.util.*

class EndFragmentViewModel (
    val database : TranslationDBDao,
    application: Application): AndroidViewModel (application) {




    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var wrongTranslations : Queue<Translation> = ArrayDeque<Translation>()





    private var _gerString = MutableLiveData<String>()
    val gerString : LiveData<String>
        get() = _gerString

    private var _esString = MutableLiveData<String>()
    val esString : LiveData<String>
        get() = _esString

    private var _infoString = MutableLiveData<String>()
    val infoString : LiveData<String>
        get() = _infoString

    private var _statusString = MutableLiveData<String>()
    val statusString : LiveData<String>
        get() = _statusString

    //var gerString : String = ""
    //var esString : String = ""
    //var info : String = ""




    private suspend fun getWrongTranslations(keys : Queue<Long>) {
        withContext(Dispatchers.IO) {
            while (keys.isNotEmpty()) {
                wrongTranslations.add(database.getTranslationByKey(keys.poll()))

            }
        }

        var lists = formatTranslationForEndView(wrongTranslations)

        _gerString.value = lists.get(0)
        _esString.value = lists.get(1)
        _infoString.value = lists.get(2)
    }


    fun onGetWrongTranslations(keys: Queue<Long>) {
        uiScope.launch {
            getWrongTranslations(keys)
        }
    }



}
