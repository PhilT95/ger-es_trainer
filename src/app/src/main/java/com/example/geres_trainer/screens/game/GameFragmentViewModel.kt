package com.example.geres_trainer.screens.game

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.geres_trainer.R
import com.example.geres_trainer.database.Translation
import com.example.geres_trainer.database.TranslationDBDao
import com.example.geres_trainer.formatTimeLeft
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class GameFragmentViewModel (
    database: TranslationDBDao,
    application: Application) : AndroidViewModel(application) {

    private val viewmodelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewmodelJob)
    private val translations = database.getAllTranslations()

    private var _timerIsFinished = MutableLiveData<Boolean>()
    val timerIsFinished : LiveData<Boolean>
        get() = _timerIsFinished



    private val gameSize = R.integer.defaultGameSize
    private val randomList = createRandomList()

    private var wordCounter : Int = 0
    private var answerWord : String = ""

    private var points : Int = 0

    var questionWord : String = ""
    var timerText : String = (R.integer.defaultTimeMilli/1000).toString()
    var pointsText : String = ""


    val timer = object : CountDownTimer(R.integer.defaultTimeMilli.toLong(), 1000) {

        override fun onTick(millisUntilFinished: Long) {
            timerText = formatTimeLeft(millisUntilFinished)
        }

        override fun onFinish() {
            _timerIsFinished.value = true
        }


    }





    fun onConfirmClick (userAnswer : String) {
        if(wordCounter < gameSize) {
            if(answerWord == userAnswer) {
                points++
                updatePointText()
                newWord()
            }
            else {
                newWord()
            }
        }
        else {
            if (answerWord == userAnswer) {
                points++
            }

            gameFinish()

        }


    }

    private fun updatePointText () {
        pointsText = points.toString() + "\t/\t" + gameSize
    }




    fun createRandomGame () {
        _timerIsFinished.value = false
        newWord()
        timer.start()


    }


    private fun newWord () {
        questionWord = randomList.get(wordCounter).wordGer
        answerWord = randomList.get(wordCounter).wordES
        wordCounter++
    }

    private fun createRandomList() : List<Translation> {

        val shuffledList = translations.value?.shuffled()

        var list : List<Translation> = emptyList()

        for (x in 0..gameSize) {
            list.plus(shuffledList?.get(1))
        }

        return list
    }

    private fun gameFinish() {

    }
}