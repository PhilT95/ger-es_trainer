package com.example.geres_trainer.screens.game

import android.app.Application
import androidx.lifecycle.*
import com.example.geres_trainer.R
import com.example.geres_trainer.database.translation.Translation
import com.example.geres_trainer.database.translation.TranslationDBDao
import com.example.geres_trainer.util.CountDownTimerPausable
import kotlinx.coroutines.*
import java.util.*


class GameFragmentViewModel (
    val lifecycle: Lifecycle,
    val database: TranslationDBDao,
    application: Application) : AndroidViewModel(application), LifecycleObserver {



    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
   var gameIsAlreadyRunning = false


    private var _listIsFilled = MutableLiveData<Boolean>()
    val listIsFilled : LiveData<Boolean>
        get() = _listIsFilled

    private var _gameIsDone = MutableLiveData<Boolean>()
    val gameIsDone : LiveData<Boolean>
        get() = _gameIsDone

    private var _showSnackBarCorrect = MutableLiveData<Boolean>()
    val showSnackBarCorrect : LiveData<Boolean>
        get() = _showSnackBarCorrect

    private var _showSnackBarFalse = MutableLiveData<Boolean>()
    val showSnackBarFalse : LiveData<Boolean>
        get() = _showSnackBarFalse





    var gameSize = application.resources.getInteger(R.integer.defaultGameSize)
    private val gameTimeDefault = application.resources.getInteger(R.integer.defaultTimeMilli)
    private var gameTime : Long = gameTimeDefault.toLong()

    private var randomList : List<Translation> = emptyList()

    private var wordCounter : Int = 0

    var answerWord : String = ""
    var points : Int = 0
    var wrongTranslations : Queue<Long> = ArrayDeque<Long>()

    private var _questionWord = MutableLiveData<String>()
    val questionWord : LiveData<String>
        get() = _questionWord

    private var _timerText = MutableLiveData<String>()
    val timerText : LiveData<String>
        get() = _timerText

    private var _pointsText = MutableLiveData<String>()
    val pointsText : LiveData<String>
        get() = _pointsText

    private var _gameProgress = MutableLiveData<Int>()
    val gameProgress : LiveData<Int>
        get() = _gameProgress



    private val timer = object : CountDownTimerPausable(gameTime.toLong(), 1000) {

        override fun onTick(millisUntilFinished: Long) {
            _timerText.value = (millisUntilFinished/1000).toString() + " seconds left"

        }
        override fun onFinish() {
           gameFinish()
        }
    }


    /**
     * Does the logic when an answer gets confirmed.
     * @param userAnswer the answer the user submitted.
     */
    fun onConfirmClick (userAnswer : String) {
        _gameProgress.value = _gameProgress.value!! + 1
        if(wordCounter < gameSize) {
            if(answerWord == userAnswer.trim()) {
                points++
                updatePointText()
                _showSnackBarCorrect.value = true
                newWord()
            }
            else {
                updatePointText()
                _showSnackBarFalse.value = true
                wrongTranslations.add(randomList.get(wordCounter-1).translationID)
                newWord()
            }
        }
        else {
            if (answerWord == userAnswer.trim()) {
                points++
            }
            else {
                wrongTranslations.add(randomList.get(wordCounter-1).translationID)
            }
            gameFinish()

        }
    }


    private fun updatePointText () {
        _pointsText.value = points.toString() + "\t/" + "\t" + gameSize.toString()
    }


    /**
     * This function is used to initialize everything for the game.
     * With the gameIsAlreadyRunning variable a safe guard was added.
     * This way configuration changes of the device are caught and handled.
     */
    fun initRandomGame () {
        if (!gameIsAlreadyRunning) {
            _listIsFilled.value = false
            _gameProgress.value = 0
            updatePointText()
            onInit()

        }


    }

    /**
     * Creates a shuffled list of all database entries and puts it into the randomList variable.
     * To notify the Fragment of this change the _listIsFilled variable is set to true.
     */
    private suspend fun initRandomList () {
        var list = emptyList<Translation>()
        withContext(Dispatchers.IO) {
            list = database.getAllTranslationsNotLive().shuffled().subList(0,gameSize)
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
        gameIsAlreadyRunning = true
        timer.start()
        newWord()
    }


    private fun newWord () {
        _questionWord.value = randomList.get(wordCounter).wordGer
        answerWord = randomList.get(wordCounter).wordES
        wordCounter++

    }



    fun doneShowCorrectSnackBar() {
        _showSnackBarCorrect.value = false
    }

    fun doneShowFalseSnackBar() {
        _showSnackBarFalse.value = false
    }




    private fun gameFinish() {
        _gameIsDone.value = true
        gameIsAlreadyRunning = false
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()

    }


    /**
     * This function is bound to the LifeCycle-Stop Event.
     * It pauses the CountDownTimer
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopTimer(){
        timer.pause()

    }

    /**
     * This function is bound to the LifeCycle-Resume Event.
     * It resumes the CountDownTimer
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resumeTimer(){

        timer.start()
    }












}

