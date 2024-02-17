package com.example.finaldraw

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.activityViewModels
import com.example.finaldraw.databinding.FragmentClickBinding

class ClickFragment : Fragment() {


    private var buttonFunction : () -> Unit = {}

    fun setButtonFunction(newFunc: () -> Unit) {
        buttonFunction = newFunc
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentClickBinding.inflate(inflater, container, false)
        val viewModel : SimpleViewModel by activityViewModels()

        binding.clickMe.setOnClickListener {
            viewModel.pickColor()
            buttonFunction()
        }

        binding.blueButton.setOnClickListener {
            viewModel.changePenColor(Color.BLUE)
            buttonFunction()
        }

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val size = progress.toFloat() // Convert the progress to a float value
                viewModel.changePenSize(size)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })


        return binding.root
    }


}