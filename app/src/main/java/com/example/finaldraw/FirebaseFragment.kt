//package com.example.finaldraw
//
//import android.os.Bundle
//import android.util.Log
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.platform.ComposeView
//import androidx.lifecycle.MutableLiveData
//import androidx.navigation.fragment.findNavController
//import com.google.firebase.Firebase
//import com.google.firebase.firestore.firestore
//import com.google.firebase.firestore.ktx.firestore
//
//
//class FirebaseFragment : Fragment() {
//    private val drawingsLiveData = MutableLiveData<List<Drawing>>()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return ComposeView(requireContext()).apply {
//            setContent {
//                DrawingListScreen(drawingsLiveData) { fileName ->
//                    // When a drawing is clicked, navigate back and pass the fileName to DrawFragment
//                    findNavController().previousBackStackEntry?.savedStateHandle?.set("fileName", fileName)
//                    findNavController().popBackStack()
//                }
//            }
//            fetchDrawingsFromFirestore()
//        }
//    }
//
//    private fun fetchDrawingsFromFirestore() {
//        Firebase.firestore.collection("drawings")
//            .get()
//            .addOnSuccessListener { documents ->
//                val drawings = documents.map { document ->
//                    Drawing(document.id, document.getString("fileName") ?: "No Name")
//                }
//                drawingsLiveData.value = drawings
//            }
//            .addOnFailureListener { exception ->
//                Log.e("Firestore", "Error getting documents: ", exception)
//            }
//    }
//
//    @Composable
//    fun DrawingListScreen(drawingsLiveData: MutableLiveData<List<Drawing>>, onDrawingClick: (String) -> Unit) {
//        val drawings by drawingsLiveData.observeAsState(initial = listOf())
//
//        Column {
//            Text("Select a Drawing", style = MaterialTheme.typography.h6, modifier = Modifier.padding(16.dp))
//            LazyColumn {
//                items(items = drawings, key = { drawing -> drawing.id }) { drawing ->
//                    ListItem(drawing, onDrawingClick)
//                }
//            }
//        }
//    }
//
//    @Composable
//    fun ListItem(drawing: Drawing, onDrawingClick: (String) -> Unit) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//                .clickable { onDrawingClick(drawing.fileName) }
//        ) {
//            Text(text = drawing.fileName, modifier = Modifier.weight(1f))
//            Icon(Icons.Filled.ArrowForward, contentDescription = "Load Drawing")
//        }
//    }
//}
