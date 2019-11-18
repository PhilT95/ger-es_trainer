package com.example.geres_trainer

import com.example.geres_trainer.database.Translation
import java.util.concurrent.Executor
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



fun formatTranslationsForView(translations : List<Translation>?) : String {

    var str : String = "German\t Spanish\t Info\n"


    for (value in translations.orEmpty()) {
        var temp : String = value.wordGer + "\t" + value.wordES + "\t" + value.info + "\n"
        str += temp
    }
    return str

}