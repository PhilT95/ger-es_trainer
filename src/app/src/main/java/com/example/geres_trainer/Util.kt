package com.example.geres_trainer

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import com.example.geres_trainer.database.Translation
import com.example.geres_trainer.database.TranslationDB
import com.example.geres_trainer.database.TranslationDBDao
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext


/*
Function to split the translations from the resource file by the delimeter ;
 */
fun splitTranslation(translation : String) : List<String> {
    return translation.split(";")
}


/*
This Function is used by the database init for threading
 */
private val IO_EXECUTOR = Executors.newSingleThreadExecutor()
fun ioThread(f: () -> Unit) {
    IO_EXECUTOR.execute(f)
}



fun formatTranslationsForView(translations : List<Translation>) : List<String> {
    var gerList = StringBuilder()
    var esList = StringBuilder()
    var info = StringBuilder()

    gerList.append("German\n")
    esList.append("Spanish\n")
    info.append("Info\n")

    for (value in translations) {
        gerList.append("${value.wordGer}\n")
        esList.append("${value.wordES}\n")
        info.append("${value.info}\n")
    }

    return listOf(gerList.toString(),esList.toString(),info.toString())




}




fun populateDatabase(database : TranslationDBDao, res: Resources) {

    val data = res.getStringArray(R.array.translations)

    for (value in data) {
        val translationData = splitTranslation(value)

        var translation = Translation()
        translation.wordGer = translationData.get(0)
        translation.wordES = translationData.get(1)
        translation.info = translationData.get(2)

        database.insert(translation)
    }
}

fun randomizeList(list : List<Translation>) : List<Translation> {
    var randomList = emptyList<Translation>()

    return randomList
}