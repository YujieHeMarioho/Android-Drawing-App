package com.example.finaldraw

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import com.example.finaldraw.databinding.FragmentDrawBinding
import androidx.navigation.fragment.findNavController

class DrawFragment : Fragment() {

    private var defaultColor = Color.BLACK // Default color
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentDrawBinding.inflate(inflater)


//        val viewModel: SimpleViewModel by activityViewModels()

        val viewModel: SimpleViewModel by activityViewModels() {
            DrawingViewModelFactory((requireContext().applicationContext as DrawingApplication).drawingRepository)}

        fun saveCurrentDrawing() {
            val fileName = binding.customView.saveDrawingToFile(requireContext())
            // Now save this fileName along with any other necessary details to the database
            val drawing = Drawing(fileName = fileName)
            viewModel.addDrawing(drawing) // Assuming you have a method in your ViewModel to handle database operations
        }

        fun loadDrawing() {
            binding.customView.loadBitmapFromFile(requireContext(), "drawing_1711055080686.png")
        }

        binding.saveBut.setOnClickListener {
            saveCurrentDrawing()
        }

        binding.loadButton.setOnClickListener {
            findNavController().navigate(R.id.action_drawFragment_to_listFragment)
            //loadDrawing()
        }

        // Observe pensize change
        viewModel.penSize.observe(viewLifecycleOwner) { size ->
            binding.customView.changePenSize(size)
        }

        // Observe pencolor change
        viewModel.penColor.observe(viewLifecycleOwner) { color ->
            binding.customView.changePenColor(color)
        }

        // Observe the color event
        viewModel.colorEvent.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { color ->
                // Prepare for drawing with the color when ready
                // Note: This assumes you want to draw immediately after picking the color,
                // and 'isReadyToDraw' is set true in 'pickColor'. Adjust as needed.
                val colorInt = Color.argb(
                    (color.alpha() * 255).toInt(),
                    (color.red() * 255).toInt(),
                    (color.green() * 255).toInt(),
                    (color.blue() * 255).toInt()
                )

                // Now wait for the readiness signal before setting up for drawing
                viewModel.isReadyToDraw.observe(viewLifecycleOwner) { ready ->
                    if (ready) { // When ready, prepare the view for drawing
                        binding.customView.prepareForCircleDrawing(true, colorInt)
                        viewModel.resetReadyToDraw() // Reset readiness to avoid repeating this action
                    }
                }
            }
        }
        return binding.root
    }
}