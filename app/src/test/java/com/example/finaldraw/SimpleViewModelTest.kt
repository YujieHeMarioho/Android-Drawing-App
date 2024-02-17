package com.example.finaldraw

import android.graphics.Color
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.junit.Rule
import org.junit.Test
import org.junit.Before
import org.mockito.Mock
import org.mockito.kotlin.any
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import org.mockito.kotlin.times

class SimpleViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SimpleViewModel

    @Mock
    private lateinit var colorObserver: Observer<Int>

    @Mock
    private lateinit var penSizeObserver: Observer<Float>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = SimpleViewModel()
    }

    @Test
    fun `when changePenSize is called, penSize is updated`() {
        viewModel.penSize.observeForever(penSizeObserver)
        val testSize = 10f
        viewModel.changePenSize(testSize)
        verify(penSizeObserver, times(1)).onChanged(testSize)
    }

    @Test
    fun `when changePenColor is called, penColor is updated`() {
        viewModel.penColor.observeForever(colorObserver)
        val testColor = 1
        viewModel.changePenColor(testColor)
        verify(colorObserver, times(1)).onChanged(testColor)
    }
}