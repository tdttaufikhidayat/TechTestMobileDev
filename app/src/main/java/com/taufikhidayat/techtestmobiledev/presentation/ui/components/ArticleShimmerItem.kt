package com.taufikhidayat.techtestmobiledev.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun ArticleShimmerItem() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column {
            // Kotak tiruan untuk Gambar
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                        .shimmerLoadingAnimation(),
            )

            Column(modifier = Modifier.padding(16.dp)) {
                // Kotak tiruan untuk Judul Baris 1
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth(0.8f)
                            .height(20.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .shimmerLoadingAnimation(),
                )
                Spacer(modifier = Modifier.height(8.dp))
                // Kotak tiruan untuk Judul Baris 2
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth(0.5f)
                            .height(20.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .shimmerLoadingAnimation(),
                )
                Spacer(modifier = Modifier.height(16.dp))
                // Kotak tiruan untuk Deskripsi
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(14.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .shimmerLoadingAnimation(),
                )
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier =
                        Modifier
                            .fillMaxWidth(0.9f)
                            .height(14.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .shimmerLoadingAnimation(),
                )
            }
        }
    }
}
