package com.example.finaldraw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.ComposeView
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

// Make sure to import your Drawing class
import com.example.finaldraw.Drawing

class ListFragment : Fragment() {
    private val viewModel: SimpleViewModel by viewModels {
        DrawingViewModelFactory((requireActivity().application as DrawingApplication).drawingRepository)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }
//    //val list by viewModel.drawings.collectAsState(initial = listOf())
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        return ComposeView(requireContext()).apply {
//            setContent {
//                val drawings by viewModel.drawings.collectAsState(initial = listOf())
//                DrawingListScreen(drawings, onNavigateBack = {
//                    // This lambda is called when the back button is pressed or a drawing is selected
//                    findNavController().popBackStack()
//                }, onDrawingSelected = { selectedDrawing ->
//                    // Pass the selected drawing back
//                    setFragmentResult(
//                        "requestKey",
//                        bundleOf("drawingFileName" to selectedDrawing.fileName)
//                    )
//                    findNavController().popBackStack()
//                })
//            }
//        }
//    }
//    @Composable
//    fun DrawingListScreen(drawings: List<Drawing>, onNavigateBack: () -> Unit, onDrawingSelected: (Drawing) -> Unit) {
//        Scaffold(
//            topBar = {
//                TopAppBar(title = { Text("Select a Drawing") }, navigationIcon = {
//                    IconButton(onClick = onNavigateBack) {
//                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
//                    }
//                })
//            }
//        ) { padding ->
//            DrawingListComposable(drawings, onDrawingClick = onDrawingSelected, modifier = Modifier.padding(padding))
//        }
//    }
//
//    @Composable
//    fun DrawingListComposable(drawings: List<Drawing>, onDrawingClick: (Drawing) -> Unit, modifier: Modifier = Modifier) {
//        LazyColumn(modifier = modifier) {
//            items(drawings) { drawing ->
//                DrawingListItem(drawing, onDrawingClick)
//            }
//        }
//    }
//
//    @Composable
//    fun DrawingListItem(drawing: Drawing, onDrawingClick: (Drawing) -> Unit) {
//        // Item UI
//        ListItem(modifier = Modifier.clickable { onDrawingClick(drawing) }) {
//            Text(text = drawing.fileName)
//        }
//    }
}