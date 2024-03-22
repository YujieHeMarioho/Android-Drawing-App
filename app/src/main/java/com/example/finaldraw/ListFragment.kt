package com.example.finaldraw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import androidx.compose.foundation.lazy.LazyColumn
import androidx.navigation.fragment.findNavController


import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.lazy.items

import androidx.compose.runtime.getValue

import androidx.compose.ui.unit.dp



import androidx.compose.material3.Text
import androidx.fragment.app.activityViewModels


// Make sure to import your Drawing class
import com.example.finaldraw.Drawing
import java.lang.reflect.Modifier

class ListFragment : Fragment() {
    val viewModel: SimpleViewModel by activityViewModels() {
        DrawingViewModelFactory((requireActivity().application as DrawingApplication).drawingRepository)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                DrawingListScreen(viewModel = viewModel) { fileName ->
                    // Set the fileName in the shared ViewModel
                    viewModel.setFileName(fileName)

                    // Navigate back to DrawFragment
                    findNavController().navigate(R.id.action_listFragment_to_drawFragment)
                }
            }
        }
    }

    @Composable
    fun DrawingListScreen(viewModel: SimpleViewModel, onDrawingClick: (String) -> Unit) {
        val drawings by viewModel.drawings.collectAsState(initial = listOf())
        LazyColumn {
            items(items = drawings, key = { drawing -> drawing.id }) { drawing ->
                ListItem(drawing = drawing, onDrawingClick = onDrawingClick)
            }
        }
    }
//
//
//
    @Composable
    fun ListItem(drawing: Drawing, onDrawingClick: (String) -> Unit) {
        // Assuming you have a basic UI for each item
        Text(
            text = drawing.fileName,
            modifier = androidx.compose.ui.Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { onDrawingClick(drawing.fileName) }
        )
    }}


