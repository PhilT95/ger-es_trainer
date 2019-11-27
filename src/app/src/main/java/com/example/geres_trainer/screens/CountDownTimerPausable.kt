package com.example.geres_trainer.screens

import android.R.string.cancel
import android.R.attr.start
import android.os.CountDownTimer


/**
 * This class uses the native CountDownTimer to
 * create a timer which could be paused and then
 * started again from the previous point. You can
 * provide implementation for onTick() and onFinish()
 * then use it in your projects.
 */
abstract class CountDownTimerPausable(millisInFuture: Long, countDownInterval: Long) {
    internal var millisInFuture: Long = 0
    internal var countDownInterval: Long = 0
    internal var millisRemaining: Long = 0

    internal var countDownTimer: CountDownTimer? = null

    var isPaused = true
        internal set

    init {
        this.millisInFuture = millisInFuture
        this.countDownInterval = countDownInterval
        this.millisRemaining = this.millisInFuture
    }

    private fun createCountDownTimer() {
        countDownTimer = object : CountDownTimer(millisRemaining, countDownInterval) {

            override fun onTick(millisUntilFinished: Long) {
                millisRemaining = millisUntilFinished
                this@CountDownTimerPausable.onTick(millisUntilFinished)

            }

            override fun onFinish() {
                this@CountDownTimerPausable.onFinish()

            }
        }
    }

    /**
     * Callback fired on regular interval.
     *
     * @param millisUntilFinished The amount of time until finished.
     */
    abstract fun onTick(millisUntilFinished: Long)

    /**
     * Callback fired when the time is up.
     */
    abstract fun onFinish()

    /**
     * Cancel the countdown.
     */
    fun cancel() {
        if (countDownTimer != null) {
            countDownTimer!!.cancel()
        }
        this.millisRemaining = 0
    }

    /**
     * Start or Resume the countdown.
     * @return CountDownTimerPausable current instance
     */
    @Synchronized
    fun start(): CountDownTimerPausable {
        if (isPaused) {
            createCountDownTimer()
            countDownTimer!!.start()
            isPaused = false
        }
        return this
    }

    /**
     * Pauses the CountDownTimerPausable, so it could be resumed(start)
     * later from the same point where it was paused.
     */
    @Throws(IllegalStateException::class)
    fun pause() {
        if (isPaused == false) {
            countDownTimer!!.cancel()
        } else {
            throw IllegalStateException("CountDownTimerPausable is already in pause state, start counter before pausing it.")
        }
        isPaused = true
    }
}