package com.example.geres_trainer.util

import android.content.res.Resources
import com.example.geres_trainer.R
import com.example.geres_trainer.database.Translation
import com.example.geres_trainer.database.TranslationDBDao
import java.util.*
import java.util.concurrent.Executors


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
        if(value.info.length > 45){
            val chunked = value.info.chunked(45)
            for (value_chunked in chunked) {
                info.append("${value_chunked}\n")
                gerList.append("\n")
                esList.append("\n")
            }
            gerList.append("\n")
            esList.append("\n")
        }
        else{
            info.append("${value.info}\n")
        }

    }

    return listOf(gerList.toString(),esList.toString(),info.toString())




}

fun formatTranslationForEndView(translations : Queue<Translation>) : List<String> {
    var gerList = StringBuilder()
    var esList = StringBuilder()
    var info = StringBuilder()

    while(translations.isNotEmpty()) {
        val value = translations.poll()
        gerList.append("${value.wordGer}\n")
        esList.append("${value.wordES}\n")
        if (value.info.length > 45) {
            val chunked = value.info.chunked(45)
            for (value_chunked in chunked) {
                info.append("${value_chunked}\n")
                gerList.append("\n")
                esList.append("\n")
            }
            gerList.append("\n")
            esList.append("\n")

        }
        else {
            info.append("${value.info}\n")
        }
    }


    return listOf(gerList.toString(),esList.toString(),info.toString())
}




fun populateDatabase(database : TranslationDBDao, res: Resources) {

    val data = res.getStringArray(R.array.translations)

    for (value in data) {
        val translationData =
            splitTranslation(value)

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

fun keyToStringEncoder(intQueue : Queue<Long>) : String {
    var keyString = StringBuilder()

    while (intQueue.isNotEmpty()) {
        keyString.append(intQueue.poll().toString() + ";")
    }

    return keyString.toString()
}

fun keyToStringDecoder(string: String) : Queue<Long> {
    var queue : Queue<Long> = ArrayDeque<Long>()

    val keys : List<String> = string.split(";")

    for (value in keys){
        if(value != ""){
           queue.add(value.toLong())
        }
    }

    return queue
}


fun keyToListDecoder(string: String) : List<Long> {


    var tmpList : MutableList<Long> = mutableListOf()

    val keys : List<String> = string.split(";")



    for (value in keys){
        if(value != ""){
            tmpList.add(value.toLong())
        }
    }

    return tmpList.toList()
}