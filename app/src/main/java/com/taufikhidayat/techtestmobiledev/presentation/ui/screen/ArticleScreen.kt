package com.taufikhidayat.techtestmobiledev.presentation.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.taufikhidayat.techtestmobiledev.data.remote.dto.ArticleDto
import com.taufikhidayat.techtestmobiledev.presentation.ui.components.ArticleShimmerItem
import com.taufikhidayat.techtestmobiledev.presentation.viewmodel.ArticleViewModel
import com.taufikhidayat.techtestmobiledev.utils.toFriendlyMessage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen(
    sourceId: String,
    sourceName: String,
    onNavigateToDetail: (String) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: ArticleViewModel = hiltViewModel(),
) {
    // Collect data Paging dari ViewModel
    val articles = viewModel.getArticles(sourceId).collectAsLazyPagingItems()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(sourceName, fontWeight = FontWeight.Bold) },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        )

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            // Render list artikel
            items(articles.itemCount) { index ->
                articles[index]?.let { article ->
                    ArticleItem(
                        article = article,
                        onClick = {
                            article.url?.let { onNavigateToDetail(it) }
                        },
                    )
                }
            }

            articles.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        items(3) {
                            ArticleShimmerItem()
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                    loadState.append is LoadState.Loading -> {
                        item {
                            ArticleShimmerItem()
                        }
                    }
                    loadState.refresh is LoadState.Error -> {
                        val e = loadState.refresh as LoadState.Error
                        item {
                            ErrorItem(
                                message = e.error.toFriendlyMessage(),
                                onRetry = { retry() },
                            )
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        val e = loadState.append as LoadState.Error
                        item {
                            ErrorItem(
                                message = e.error.toFriendlyMessage(),
                                onRetry = { retry() },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ArticleItem(
    article: ArticleDto,
    onClick: () -> Unit,
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column {
            // Image Loading menggunakan Coil
            AsyncImage(
                model = article.urlToImage,
                contentDescription = article.title,
                contentScale = ContentScale.Crop,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = article.title ?: "No Title",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = article.description ?: "",
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = article.publishedAt?.take(10) ?: "", // Ambil format YYYY-MM-DD
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray,
                )
            }
        }
    }
}

@Composable
fun ErrorItem(
    message: String,
    onRetry: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = message, color = Color.Red)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetry) {
            Text("Coba Lagi")
        }
    }
}
