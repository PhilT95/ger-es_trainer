package com.example.geres_trainer.screens.end

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.geres_trainer.database.TranslationDBDao


class EndFragmentViewModel (
    val database : TranslationDBDao,
    keys: List<Long>,
    application: Application): AndroidViewModel (application) {

    val translations = database.getTranslationsByKeys(keys.toLongArray())

}
