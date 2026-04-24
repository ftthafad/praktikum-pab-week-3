package com.travelwaka.app.ui.screens.bookmark

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.travelwaka.app.ui.components.*
import com.travelwaka.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    navController: NavController,
    onWisataClick: (String) -> Unit
) {
    // Sementara kosong, akan disambungkan ke API setelah tabel bookmarks dibuat
    val bookmarkedList = emptyList<Any>()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Tersimpan",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Primary,
                    titleContentColor = White
                )
            )
        },
        bottomBar = { BottomNavBar(navController) },
        containerColor = Background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text("🔖", style = MaterialTheme.typography.displayLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Belum ada wisata tersimpan",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Simpan wisata favoritmu agar mudah ditemukan",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookmarkScreenPreview() {
    TravelWakaTheme {
        BookmarkScreen(
            navController = rememberNavController(),
            onWisataClick = {}
        )
    }
}