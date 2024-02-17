package com.example.finaldraw

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class SimpleViewModel : ViewModel() {
    private val _colorEvent = MutableLiveData<Event<Color>>()
    val colorEvent: LiveData<Event<Color>> = _colorEvent

    // LiveData to manage readiness for drawing a circle
    private val _isReadyToDraw = MutableLiveData<Boolean>()
    val isReadyToDraw: LiveData<Boolean> = _isReadyToDraw


    private val _penColor = MutableLiveData<Int>()
    val penColor: LiveData<Int> = _penColor

    fun changePenColor(color: Int) {
        _penColor.value = color
    }

    fun pickColor() {
        with(Random.Default) {
            val color = Color.valueOf(nextFloat(), nextFloat(), nextFloat())
            _colorEvent.value = Event(color)
            _isReadyToDraw.value = true // Indicate readiness to draw a circle upon next touch
        }
    }

    // Call this method to reset readiness (after drawing a circle, for example)
    fun resetReadyToDraw() {
        _isReadyToDraw.value = false
    }
}

class Event<out T>(private val content: T) {
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        if (!hasBeenHandled) {
            hasBeenHandled = true
            return content
        }
        return null
    }
}