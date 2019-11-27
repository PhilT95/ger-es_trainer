package com.example.geres_trainer.screens.end

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import java.lang.IllegalArgumentException

class EndFragmentViewModelFactory (
    private val application: Application) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(EndFragmentViewModel::class.java)){
            return EndFragmentViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModelClass")
    }
}