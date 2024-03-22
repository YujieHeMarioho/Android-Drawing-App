package com.example.finaldraw

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.random.Random

class SimpleViewModel(private val repository: DrawingRepository) : ViewModel() {

    // Expose the flow provided by the dao, instantiated in the Repository
    val drawings: Flow<List<Drawing>> = repository.allDrawings


    fun addDrawing(drawing: Drawing) = viewModelScope.launch {
        repository.insertDrawing(drawing)
    }

    private val _colorEvent = MutableLiveData<Event<Color>>()
    val colorEvent: LiveData<Event<Color>> = _colorEvent

    // LiveData to manage readiness for drawing a circle
    private val _isReadyToDraw = MutableLiveData<Boolean>()
    val isReadyToDraw: LiveData<Boolean> = _isReadyToDraw

    private val _penColor = MutableLiveData<Int>()
    val penColor: LiveData<Int> = _penColor

    private val _penSize = MutableLiveData<Float>()
    val penSize: LiveData<Float> = _penSize

    private val _saveDrawingEvent = MutableLiveData<Boolean>()
    val saveDrawingEvent: LiveData<Boolean> = _saveDrawingEvent

    private val _fileName = MutableLiveData<String>()
    val fileName: LiveData<String> = _fileName

    fun triggerSaveDrawing() {
        _saveDrawingEvent.value = true;
    }

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

    fun changePenSize(size: Float) {
        _penSize.value = size
    }

    // Call this method to reset readiness (after drawing a circle, for example)
    fun resetReadyToDraw() {
        _isReadyToDraw.value = false
    }

    fun setFileName(name: String) {
        _fileName.value = name // This will notify observers
    }
//    fun string getSelectedFileName{
//        return fileName;
//    }

    fun deleteAllDrawings() = viewModelScope.launch {
        repository.deleteAllDrawings()
        // Now, delete all files from internal storage
        //deleteAllFilesFromInternalStorage()
    }

    //fun getAllDrawings(): Flow<List<Drawing>> =

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
// This factory class allows us to define custom constructors for the view model
class DrawingViewModelFactory(private val repository: DrawingRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SimpleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SimpleViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}