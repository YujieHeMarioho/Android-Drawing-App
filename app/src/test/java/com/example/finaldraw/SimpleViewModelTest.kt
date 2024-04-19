package com.example.finaldraw

import androidx.lifecycle.Observer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class SimpleViewModelTest {

    @get:Rule
    //var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SimpleViewModel

    @Mock
    private lateinit var colorObserver: Observer<Int>

    @Mock
    private lateinit var penSizeObserver: Observer<Float>

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
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