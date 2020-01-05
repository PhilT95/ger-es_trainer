package com.example.geres_trainer.screens.title

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.geres_trainer.database.config.ConfigurationDAO
import com.example.geres_trainer.database.translation.TranslationDBDao
import java.lang.IllegalArgumentException

class TitleFragmentViewModelFactory(
    private val dataSource: TranslationDBDao,
    private val configSource: ConfigurationDAO,
    private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TitleFragmentViewModel::class.java)) {
            return TitleFragmentViewModel(dataSource, configSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}