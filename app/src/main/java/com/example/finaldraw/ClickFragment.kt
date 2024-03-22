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
import yuku.ambilwarna.AmbilWarnaDialog
import androidx.navigation.fragment.findNavController


class ClickFragment : Fragment() {

    private var defaultColor = Color.BLACK // Initialize with black or any default color
    private var buttonFunction : () -> Unit = {}
    
    val viewModel: SimpleViewModel by activityViewModels() {
            DrawingViewModelFactory((requireContext().applicationContext as DrawingApplication).drawingRepository)}


        fun setButtonFunction(newFunc: () -> Unit) {
        buttonFunction = newFunc
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentClickBinding.inflate(inflater, container, false)

        binding.circle.setOnClickListener {
            viewModel.pickColor()
            buttonFunction()
        }

        binding.colorPicker.setOnClickListener {
            openColorPicker()

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

    private fun openColorPicker() {
        val colorPicker = AmbilWarnaDialog(context, defaultColor, true, object : AmbilWarnaDialog.OnAmbilWarnaListener {
            override fun onCancel(dialog: AmbilWarnaDialog?) {
                // Handle cancel
            }

            override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                // Update default color and change pen color in ViewModel
                defaultColor = color
                //val viewModel: SimpleViewModel by activityViewModels()
                viewModel.changePenColor(color)
            }
        })
        colorPicker.show()
    }

}