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
