package com.travelwaka.app.ui.screens.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.travelwaka.app.ui.components.*
import com.travelwaka.app.ui.theme.*
import com.travelwaka.app.viewmodel.WisataViewModel

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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailWisataScreen(
    wisataId: String,
    navController: NavController,
    onBack: () -> Unit,
    onWriteReview: () -> Unit,
    token: String? = null
) {
    val context = LocalContext.current
    val viewModel = remember { WisataViewModel() }
    val wisata by viewModel.wisataDetail.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isBookmarked by viewModel.isBookmarked.collectAsState()
    val bookmarkMessage by viewModel.bookmarkMessage.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(wisataId) {
        viewModel.getWisataDetail(wisataId.toIntOrNull() ?: 0)
        token?.let { viewModel.checkBookmark(it, wisataId.toIntOrNull() ?: 0) }
    }

    LaunchedEffect(bookmarkMessage) {
        bookmarkMessage?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearBookmarkMessage()
        }
    }

    val photos = wisata?.photos ?: emptyList()
    val pagerState = rememberPagerState(pageCount = { if (photos.isEmpty()) 1 else photos.size })

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Primary)
                }
            } else if (wisata == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Wisata tidak ditemukan", color = TextSecondary)
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 80.dp)
                ) {
                    // Foto Carousel
                    item {
                        Box(modifier = Modifier.fillMaxWidth().height(280.dp)) {
                            HorizontalPager(state = pagerState, modifier = Modifier.fillMaxSize()) { page ->
                                AsyncImage(
                                    model = if (photos.isEmpty()) "" else photos[page].photo_url,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                            IconButton(
                                onClick = onBack,
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(8.dp)
                                    .background(Color.Black.copy(alpha = 0.4f), CircleShape)
                            ) {
                                Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = White)
                            }
                            if (photos.size > 1) {
                                Row(
                                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 8.dp),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    repeat(photos.size) { index ->
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
                                            text = wisata!!.name,
                                            style = MaterialTheme.typography.headlineMedium,
                                            fontWeight = FontWeight.Bold,
                                            color = TextPrimary
                                        )
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Icon(Icons.Filled.LocationOn, contentDescription = null, tint = Primary, modifier = Modifier.size(16.dp))
                                            Text(wisata!!.location, style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                                        }
                                    }
                                    Row {
                                        IconButton(onClick = {
                                            token?.let {
                                                viewModel.toggleBookmark(it, wisataId.toIntOrNull() ?: 0)
                                            }
                                        }) {
                                            Icon(
                                                imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                                                contentDescription = "Bookmark",
                                                tint = if (isBookmarked) Primary else TextSecondary
                                            )
                                        }
                                        IconButton(onClick = {
                                            val shareText = """
                                                🌿 ${wisata!!.name}
                                                📍 ${wisata!!.location}
                                                💰 ${wisata!!.price}
                                                ⭐ ${wisata!!.rating}
                                                
                                                Temukan wisata menarik di Jawa Tengah dengan Travel Waka!
                                                (Aplikasi masih dalam tahap pengembangan)
                                            """.trimIndent()

                                            val intent = android.content.Intent(android.content.Intent.ACTION_SEND).apply {
                                                type = "text/plain"
                                                putExtra(android.content.Intent.EXTRA_TEXT, shareText)
                                            }
                                            context.startActivity(
                                                android.content.Intent.createChooser(intent, "Bagikan via")
                                            )
                                        }) {
                                            Icon(Icons.Filled.Share, contentDescription = "Share", tint = TextSecondary)
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))
                                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                                    InfoChip(icon = Icons.Filled.Star, text = wisata!!.rating.toString(), color = StarColor)
                                    InfoChip(icon = Icons.Filled.ConfirmationNumber, text = wisata!!.price, color = Primary)
                                    InfoChip(icon = Icons.Filled.Category, text = wisata!!.category?.name ?: "-", color = PrimaryMedium)
                                }

                                Spacer(modifier = Modifier.height(16.dp))
                                Divider(color = DividerColor)
                                Spacer(modifier = Modifier.height(16.dp))

                                Text("Jam Operasional", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Filled.AccessTime, contentDescription = null, tint = Primary, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(wisata!!.opening_hours ?: "-", style = MaterialTheme.typography.bodyMedium, color = TextSecondary)
                                }

                                Spacer(modifier = Modifier.height(16.dp))
                                Text("Tentang Tempat Ini", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = wisata!!.description,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = TextSecondary
                                )

                                Spacer(modifier = Modifier.height(16.dp))
                                Divider(color = DividerColor)
                                Spacer(modifier = Modifier.height(16.dp))

                                Text("Lokasi", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold, color = TextPrimary)
                                Spacer(modifier = Modifier.height(8.dp))
                                if (wisata!!.latitude != null && wisata!!.longitude != null) {
                                    OsmMapView(
                                        latitude = wisata!!.latitude!!,
                                        longitude = wisata!!.longitude!!,
                                        title = wisata!!.name,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(200.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    OutlinedButton(
                                        onClick = {
                                            val uri = android.net.Uri.parse(
                                                "google.navigation:q=${wisata!!.latitude},${wisata!!.longitude}&mode=d"
                                            )
                                            val intent = android.content.Intent(
                                                android.content.Intent.ACTION_VIEW, uri
                                            )
                                            intent.setPackage("com.google.android.apps.maps")
                                            context.startActivity(intent)
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(12.dp),
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Primary),
                                        border = androidx.compose.foundation.BorderStroke(1.5.dp, Primary)
                                    ) {
                                        Icon(
                                            Icons.Filled.Directions,
                                            contentDescription = null,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            "Get Direction",
                                            style = MaterialTheme.typography.titleSmall,
                                            fontWeight = FontWeight.SemiBold
                                        )
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

                    items(dummyReviews) { review ->
                        ReviewCard(review = review, modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp))
                    }
                }
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