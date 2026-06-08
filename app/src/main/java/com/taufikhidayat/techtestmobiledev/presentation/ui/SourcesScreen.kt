package com.taufikhidayat.techtestmobiledev.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taufikhidayat.techtestmobiledev.presentation.viewmodel.NewsViewModel

@Composable
fun SourcesScreen(
    viewModel: NewsViewModel,
    onSourceClick: (String) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadSources("business") // sementara static dulu
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        if (state.isLoading) {
            CircularProgressIndicator()
        }

        state.error?.let {
            Text("Error: $it", color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(8.dp))

        state.sources.forEach { source ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { onSourceClick(source.id) }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(source.name, style = MaterialTheme.typography.titleMedium)
                    Text(source.description)
                }
            }
        }
    }
}