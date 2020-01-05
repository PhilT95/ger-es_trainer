package com.example.geres_trainer.screens.game

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.geres_trainer.database.translation.TranslationDBDao
import java.lang.IllegalArgumentException

class GameFragmentViewModelFactory(
    private val lifecycle: Lifecycle,
    private val dataSource: TranslationDBDao,
    private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GameFragmentViewModel::class.java)) {
            return GameFragmentViewModel(lifecycle, dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}