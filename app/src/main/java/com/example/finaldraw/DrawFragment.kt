/**
 * Matthew Goh, Yujie He, Eric Rapp
 * DrawFragment observes changes to liveData in the viewModel to update the CustomView
 * Also contains some event listeners for the save and load buttons.
 */
package com.example.finaldraw



import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.finaldraw.databinding.FragmentDrawBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.storage
import java.io.ByteArrayOutputStream

class DrawFragment : Fragment() {

    private var defaultColor = Color.BLACK // Default color
    private var _binding: FragmentDrawBinding? = null
    private val binding get() = _binding!!


    private val viewModel: SimpleViewModel by activityViewModels() {
        DrawingViewModelFactory((requireContext().applicationContext as DrawingApplication).drawingRepository)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentDrawBinding.inflate(inflater)

        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // User is signed in, navigate directly to the DrawFragment or the main content area
            binding.loginText.text = "logged in as: " + user.email
        } else {
            binding.loginText.text = "Not logged in"
        }

        fun saveCurrentDrawing() {
            val fileName = binding.customView.saveDrawingToFile(requireContext())
            // Now save this fileName along with any other necessary details to the database
            val drawing = Drawing(fileName = fileName)
            viewModel.addDrawing(drawing) // Assuming you have a method in your ViewModel to handle database operations
        }

        fun saveDrawingToFirebase() {
            val fileName = "drawing_${System.currentTimeMillis()}.png"
            val byteArrayOutputStream = ByteArrayOutputStream()
            val bitmap = binding.customView.getBitmap()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            val data = byteArrayOutputStream.toByteArray()

            val storageRef = Firebase.storage.reference
            val fileRef = storageRef.child("${user!!.uid}/fileName")
            var uploadTask = fileRef.putBytes(data)
                uploadTask
                .addOnFailureListener{ e -> Log.e("PICUPLOAD", "Failed !$e")}
                .addOnSuccessListener { Log.e("PICUPLOAD", "Success!") }
        }

        binding.saveBut.setOnClickListener {
            saveCurrentDrawing()
        }

        binding.SaveCloud.setOnClickListener {
            saveDrawingToFirebase()
        }

        binding.loadButton.setOnClickListener {
            findNavController().navigate(R.id.action_drawFragment_to_listFragment)
        }

        binding.loginButton.setOnClickListener {
            val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
            if (user == null)
                findNavController().navigate(R.id.action_drawFragment_to_loginFragment)
            else
            {
                AlertDialog.Builder(context)
                    .setTitle("You are already Logged In as: " + user.email)
                    .setMessage("Click anywhere to cancel.")
                    .setPositiveButton("OK") { dialog, which ->
                        dialog.dismiss()
                    }
                    .setPositiveButton("Log Out") { dialog, which ->
                        FirebaseAuth.getInstance().signOut() // Log out the user
                        binding.loginText.text = "Not Logged In" // Set text to logged out
                        dialog.dismiss()
//                        findNavController().navigate(R.id.action_drawFragment_to_loginFragment)
                    }
                    .create()
                    .show()
            }
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

        _binding?.buttonAddNoise?.setOnClickListener {
            _binding?.customView?.applyNoise()
        }

        _binding?.buttonInvertColors?.setOnClickListener {
            _binding?.customView?.applyInvertColors()
        }

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.fileName.observe(viewLifecycleOwner){ fileName ->
            binding.customView.loadBitmapFromFile(requireContext(),fileName)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Nullify the binding when the view is destroyed to avoid memory leaks
        _binding = null
    }
}