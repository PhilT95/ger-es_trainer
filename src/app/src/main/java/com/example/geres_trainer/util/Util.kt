package com.example.geres_trainer.util

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


/*
Encodes a Queue of Long values.
Used to a list of translationIDs in a very simple way to another fragment.
 */
fun keyToStringEncoder(intQueue : Queue<Long>) : String {
    var keyString = StringBuilder()

    while (intQueue.isNotEmpty()) {
        keyString.append(intQueue.poll().toString() + ";")
    }

    return keyString.toString()
}



/*
Decodes the string decoded by the keytoStringEncoder function back.
Returns a List of TranslationIDs (Long)
 */
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