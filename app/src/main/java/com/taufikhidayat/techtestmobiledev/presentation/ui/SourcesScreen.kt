package com.taufikhidayat.techtestmobiledev.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taufikhidayat.techtestmobiledev.domain.model.Source
import com.taufikhidayat.techtestmobiledev.presentation.viewmodel.NewsViewModel

@Composable
fun SourcesScreen(
    viewModel: NewsViewModel,
    onSourceClick: (String) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        // ---------------- LOADING ----------------
        if (state.isLoading) {
            CircularProgressIndicator()
        }

        // ---------------- ERROR ----------------
        state.error?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ---------------- EMPTY STATE ----------------
        if (state.sources.isEmpty() && !state.isLoading) {
            Text("No sources available")
        }

        // ---------------- LIST ----------------
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.sources) { source: Source ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onSourceClick(source.id)
                        }
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = source.name,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = source.description,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}