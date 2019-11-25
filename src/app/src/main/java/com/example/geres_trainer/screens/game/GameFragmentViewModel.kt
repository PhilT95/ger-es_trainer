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
import com.example.geres_trainer.randomizeList
import kotlinx.coroutines.*





class GameFragmentViewModel (
    val database: TranslationDBDao,
    application: Application) : AndroidViewModel(application) {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _timerIsFinished = MutableLiveData<Boolean>()
    val timerIsFinished : LiveData<Boolean>
        get() = _timerIsFinished

    private var _listIsFilled = MutableLiveData<Boolean>()
    val listIsFilled : LiveData<Boolean>
        get() = _listIsFilled





    private val gameSize = application.resources.getInteger(R.integer.defaultGameSize)
    private val gameTime = application.resources.getInteger(R.integer.defaultTimeMilli)
    private var randomList : List<Translation> = emptyList()

    private var wordCounter : Int = 0
    private var answerWord : String = ""

    private var points : Int = 0

    private var _questionWord = MutableLiveData<String>()
    val questionWord : LiveData<String>
        get() = _questionWord

    private var _timerText = MutableLiveData<String>()
    val timerText : LiveData<String>
        get() = _timerText

    private var _pointsText = MutableLiveData<String>()
    val pointsText : LiveData<String>
        get() = _pointsText






    private val timer = object : CountDownTimer(gameTime.toLong(), 1000) {
        override fun onTick(millisUntilFinished: Long) {
            _timerText.value = (millisUntilFinished/1000).toString()
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
                updatePointText()
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
        _pointsText.value = points.toString() + "\t/" + "\t" + gameSize.toString()
    }




    fun initRandomGame () {
        _timerIsFinished.value = false
        _listIsFilled.value = false
        updatePointText()
        onInit()

    }

    private suspend fun initRandomList () {
        var list = emptyList<Translation>()
        withContext(Dispatchers.IO) {
            list = database.getAllTranslationsNotLive().shuffled()
        }
        randomList = list
        _listIsFilled.value = true
    }

    private fun onInit() {
        uiScope.launch {
            initRandomList()
        }
    }

    fun startGame() {
        timer.start()
        newWord()
    }


    private fun newWord () {
        _questionWord.value = randomList.get(wordCounter).wordGer
        answerWord = randomList.get(wordCounter).wordES
        wordCounter++
    }








    private fun gameFinish() {

    }
}