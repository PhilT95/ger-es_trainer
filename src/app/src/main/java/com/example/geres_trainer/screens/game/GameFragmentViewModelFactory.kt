package com.example.geres_trainer.screens.game

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class GameFragmentViewModelFactory(
    private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(GameFragmentViewModel::class.java)) {
            return GameFragmentViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}