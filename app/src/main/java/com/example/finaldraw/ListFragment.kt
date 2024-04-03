/**
 * Matthew Goh, Yujie He, Eric Rapp
 * This class holds logic responsible for the list of drawing objects to load from.
 */
package com.example.finaldraw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.compose.foundation.lazy.LazyColumn
import androidx.navigation.fragment.findNavController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController

class ListFragment : Fragment() {
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
                DrawingListScreen(viewModel = viewModel, navController = navController) { fileName ->
                    // Set the fileName in the shared ViewModel
                    //findNavController().navigate(R.id.action_listFragment_to_drawFragment)
                    findNavController().popBackStack()
                    viewModel.setFileName(fileName)
                }
            }
        }
    }
    @Composable
    fun DrawingListScreen(viewModel: SimpleViewModel, navController: NavController, onDrawingClick: (String) -> Unit) {
        val context = LocalContext.current
        Column {
            Button(onClick = { navController.navigateUp() }) {
                Text(text = "Back")
            }

            val drawings by viewModel.drawings.collectAsState(initial = listOf())
            LazyColumn {
                items(items = drawings, key = { drawing -> drawing.id }) { drawing ->
                    ListItem(drawing = drawing, onDrawingClick = onDrawingClick)
                }
            }
        }
    }

    @Composable
    fun ListItem(drawing: Drawing, onDrawingClick: (String) -> Unit) {
        Row(
            modifier = androidx.compose.ui.Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = drawing.fileName,
                modifier = androidx.compose.ui.Modifier
                    .weight(1f) // This makes the text fill the available space, pushing the icon to the end
                    .clickable { onDrawingClick(drawing.fileName) }
                    .padding(end = 16.dp) // Add some padding to ensure text doesn't overlap with the delete icon
            )
            IconButton(onClick = { viewModel.deleteDrawing(drawing) }) {
                Icon(
                    painter = painterResource(id = R.drawable.delete_button), // Use your delete icon resource
                    contentDescription = "Delete"
                )
            }
        }
    }
}


