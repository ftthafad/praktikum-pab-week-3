package com.travelwaka.app.ui.screens.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.travelwaka.app.ui.components.*
import com.travelwaka.app.ui.theme.*

data class ReviewItem(
    val userName: String,
    val rating: Int,
    val comment: String,
    val date: String
)

val dummyReviews = listOf(
    ReviewItem("Budi Santoso", 5, "Tempat yang luar biasa! Pemandangannya sangat indah dan bikin betah.", "12 Apr 2025"),
    ReviewItem("Siti Rahayu", 4, "Bagus banget, tapi parkirannya agak susah kalau weekend.", "5 Apr 2025"),
    ReviewItem("Ahmad Fauzi", 5, "Wajib dikunjungi kalau ke Jawa Tengah!", "28 Mar 2025")
)

val detailImages = listOf(
    "https://images.unsplash.com/photo-1596402184320-417e7178b2cd?w=800",
    "https://images.unsplash.com/photo-1555400038-63f5ba517a47?w=800",
    "https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800"
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailWisataScreen(
    wisataId: String,
    navController: NavController,
    onBack: () -> Unit,
    onWriteReview: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { detailImages.size })
    var isBookmarked by remember { mutableStateOf(false) }
    val wisata = dummyWisataList.find { it.id == wisataId } ?: dummyWisataList[0]

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            // Foto Carousel
            item {
                Box(modifier = Modifier.fillMaxWidth().height(280.dp)) {
                    HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
                        AsyncImage(
                            model = detailImages[page],
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    // Back button
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(8.dp)
                            .background(Color.Black.copy(alpha = 0.4f), CircleShape)
                    ) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = White)
                    }
                    // Page indicators
                    Row(
                        modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        repeat(detailImages.size) { index ->
                            Box(
                                modifier = Modifier
                                    .size(if (pagerState.currentPage == index) 20.dp else 8.dp, 8.dp)
                                    .clip(CircleShape)
                                    .background(if (pagerState.currentPage == index) White else White.copy(alpha = 0.5f))
                            )
                        }
                    }
                }
            }

            // Info Wisata
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = (-20).dp),
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    colors = CardDefaults.cardColors(containerColor = White)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = wisata.name,
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Filled.LocationOn, contentDescription = null, tint = Primary, modifier = Modifier.size(16.dp))
                                    Text(wisata.location, style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                                }
                            }
                            Row {
                                IconButton(onClick = { isBookmarked = !isBookmarked }) {
                                    Icon(
                                        imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                                        contentDescription = "Bookmark",
                                        tint = if (isBookmarked) Primary else TextSecondary
                                    )
                                }
                                IconButton(onClick = {}) {
                                    Icon(Icons.Filled.Share, contentDescription = "Share", tint = TextSecondary)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            InfoChip(icon = Icons.Filled.Star, text = wisata.rating.toString(), color = StarColor)
                            InfoChip(icon = Icons.Filled.ConfirmationNumber, text = wisata.price, color = Primary)
                            InfoChip(icon = Icons.Filled.Category, text = wisata.category, color = PrimaryMedium)
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Divider(color = DividerColor)
                        Spacer(modifier = Modifier.height(16.dp))

                        Text("Jam Operasional", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Filled.AccessTime, contentDescription = null, tint = Primary, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Senin - Minggu: 08.00 - 17.00 WIB", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Tentang Tempat Ini", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Destinasi wisata yang menakjubkan di Jawa Tengah dengan pemandangan alam yang indah dan fasilitas yang lengkap. Cocok untuk dikunjungi bersama keluarga maupun teman-teman. Tersedia area parkir luas, toilet umum, warung makan, dan spot foto instagramable.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextSecondary
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        Divider(color = DividerColor)
                        Spacer(modifier = Modifier.height(16.dp))

                        Text("Lokasi", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                        Spacer(modifier = Modifier.height(8.dp))
                        // Map placeholder
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(PrimaryLight),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(Icons.Filled.Map, contentDescription = null, tint = Primary, modifier = Modifier.size(40.dp))
                                Text("Peta Lokasi", style = MaterialTheme.typography.bodyMedium, color = Primary)
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Divider(color = DividerColor)
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Ulasan", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                            TextButton(onClick = onWriteReview) {
                                Text("Tulis Ulasan", color = Primary)
                            }
                        }
                    }
                }
            }

            // Reviews
            items(dummyReviews.size) { index ->
                val review = dummyReviews[index]
                ReviewCard(review = review, modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp))
            }
        }
    }
}

@Composable
fun InfoChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    color: androidx.compose.ui.graphics.Color
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = color.copy(alpha = 0.12f)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(14.dp))
            Text(text, style = MaterialTheme.typography.labelMedium, color = color, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun ReviewCard(review: ReviewItem, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .background(Primary, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = review.userName.first().toString(),
                            style = MaterialTheme.typography.titleMedium,
                            color = White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Column {
                        Text(review.userName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                        Text(review.date, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                    }
                }
                DisplayRatingBar(rating = review.rating.toFloat())
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(review.comment, style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailWisataScreenPreview() {
    TravelWakaTheme {
        DetailWisataScreen(
            wisataId = "1",
            navController = rememberNavController(),
            onBack = {},
            onWriteReview = {}
        )
    }
}
