package com.example.geres_trainer.screens.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.geres_trainer.database.TranslationDBDao
import java.lang.IllegalArgumentException

class EditFragmentViewModelFactory(
    private val translationID: Long,
    private val dataSource: TranslationDBDao) : ViewModelProvider.Factory {

    @Suppress("unchecked cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(EditFragmentViewModel::class.java)){
            return EditFragmentViewModel(translationID, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}