package com.example.geres_trainer.screens.add

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.geres_trainer.database.TranslationDBDao
import java.lang.IllegalArgumentException

class AddFragmentViewModelFactory(
    private val dataSource: TranslationDBDao,
    private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddFragmentViewModel::class.java)){
            return AddFragmentViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}

