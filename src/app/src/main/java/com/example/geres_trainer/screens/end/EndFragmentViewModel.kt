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
    val keys: List<Long>,
    application: Application): AndroidViewModel (application) {




    private val viewModelJob = Job()




    val translations = database.getTranslationsByKeys(keys.toLongArray())
    val test = database.getAllTranslations()
    val x = 5+1


}
