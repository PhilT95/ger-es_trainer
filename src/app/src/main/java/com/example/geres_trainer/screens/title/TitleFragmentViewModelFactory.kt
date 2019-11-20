package com.example.geres_trainer.screens.title

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.geres_trainer.database.TranslationDBDao
import java.lang.IllegalArgumentException

class TitleFragmentViewModelFactory(
    private val dataSource: TranslationDBDao,
    private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TitleFragmentViewModel::class.java)) {
            return TitleFragmentViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}