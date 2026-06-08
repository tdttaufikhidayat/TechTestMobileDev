package com.taufikhidayat.techtestmobiledev.presentation.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.taufikhidayat.techtestmobiledev.data.remote.dto.SourceDto
import com.taufikhidayat.techtestmobiledev.presentation.state.UiState
import com.taufikhidayat.techtestmobiledev.presentation.viewmodel.SourceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SourceScreen(
    onNavigateToArticle: (String, String) -> Unit,
    viewModel: SourceViewModel = hiltViewModel()
) {
    val sourcesState by viewModel.sourcesState.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    val categories = listOf("business", "entertainment", "general", "health", "science", "sports", "technology")

    Column(modifier = Modifier.fillMaxSize()) {
        // 1. Top Bar & Search
        TopAppBar(
            title = { Text("News Sources", fontWeight = FontWeight.Bold) },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.setSearchQuery(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Cari sumber berita...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )

        // 2. Categories Row (Story #1)
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(categories) { category ->
                FilterChip(
                    selected = category == selectedCategory,
                    onClick = { viewModel.setCategory(category) },
                    label = { Text(category.replaceFirstChar { it.uppercase() }) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 3. Source List with State Handling (Story #2 & #7)
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            when (val state = sourcesState) {
                is UiState.Loading -> {
                    CircularProgressIndicator()
                }
                is UiState.Error -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = state.message, color = Color.Red, modifier = Modifier.padding(16.dp))
                        Button(onClick = { viewModel.retry() }) {
                            Text("Coba Lagi")
                        }
                    }
                }
                is UiState.Success -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.data) { source ->
                            SourceItem(source = source, onClick = {
                                onNavigateToArticle(source.id ?: "", source.name ?: "Unknown")
                            })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SourceItem(source: SourceDto, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = source.name ?: "", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = source.description ?: "", style = MaterialTheme.typography.bodyMedium, maxLines = 3)
            Spacer(modifier = Modifier.height(8.dp))
            SuggestionChip(
                onClick = { },
                label = { Text(source.category ?: "") },
                modifier = Modifier.height(24.dp)
            )
        }
    }
}