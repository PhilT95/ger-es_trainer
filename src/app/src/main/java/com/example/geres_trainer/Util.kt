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



fun formatTranslationsForView(translations : List<Translation>) : Spanned {

    var sb = StringBuilder()
    sb.apply {
        append("<table style=\"width:100%\">")
            append("<tr>")
                append("<th>German</th>")
                append("<th>Spanish</th>")
                append("<th>Info</th>")
            append("</tr>")
       translations.forEach {
           append("<tr>")
            append("<td>${it.wordGer}</td>")
            append("<td>${it.wordES}</td>")
            append("<td>${it.info}</td>")
           append("</tr>")
       }
        append("</table>")
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(sb.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        return HtmlCompat.fromHtml(sb.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }


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