package com.example.finaldraw

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

data class DrawingData(
    val fileName: String,
    val filePath: String
)

class FirebaseFragment : Fragment() {
    private val viewModel: SimpleViewModel by activityViewModels() {
        DrawingViewModelFactory((requireActivity().application as DrawingApplication).drawingRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                val navController = findNavController()
                FileNameListScreen(navController, viewModel)
            }
        }
    }
}

private fun fetchDrawingsFromFirestore(): Flow<List<DrawingData>> = flow {
    try {
        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
        val snapshot = Firebase.firestore.collection("userCollection")
            .document(user!!.uid)
            .collection("drawings")
            .get()
            .await()
        val drawings = snapshot.documents.mapNotNull { document ->
            val fileName = document.getString("fileName")
            val filePath = document.getString("filePath")
            if (fileName != null && filePath != null) {
                DrawingData(fileName, filePath)
            } else {
                null
            }
        }
        emit(drawings)
    } catch (e: Exception) {
        Log.e("Firestore", "Error getting documents: ", e)
        emit(emptyList<DrawingData>())
    }
}

@Composable
fun ListItem(drawingData: DrawingData, onItemClick: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onItemClick(drawingData.filePath) }
    ) {
        Text(text = drawingData.fileName, style = MaterialTheme.typography.bodyLarge)
    }
}
@Composable
fun FileNameListScreen(navController: NavController, viewModel: SimpleViewModel) {
    val drawingDataFlow = fetchDrawingsFromFirestore()
    val drawingData by drawingDataFlow.collectAsState(initial = listOf())

    LazyColumn {
        items(drawingData) { drawingDataItem ->
            ListItem(drawingDataItem) { selectedFilePath ->
                viewModel.setImagePath(selectedFilePath)
                navController.popBackStack() // Adjust according to your navigation graph ID
            }
        }
    }
}

