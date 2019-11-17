package com.example.geres_trainer.screens.title

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class TitleFragmentViewModelFactory(
    private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TitleFragmentViewModel::class.java)) {
            return TitleFragmentViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}