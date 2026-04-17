package com.travelwaka.app.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.travelwaka.app.ui.theme.*

data class NotifikasiItem(
    val id: String,
    val title: String,
    val message: String,
    val date: String,
    val status: String, // "pending", "approved", "rejected"
    val isRead: Boolean = false
)

val dummyNotifikasi = listOf(
    NotifikasiItem(
        id = "1",
        title = "Pengajuan Diterima",
        message = "Selamat! Pengajuan kamu sebagai pengelola wisata telah disetujui. Kamu sekarang bisa menambahkan destinasi wisata.",
        date = "17 Apr 2025",
        status = "approved",
        isRead = false
    ),
    NotifikasiItem(
        id = "2",
        title = "Pengajuan Sedang Ditinjau",
        message = "Pengajuan kamu sebagai pengelola wisata sedang dalam proses peninjauan oleh admin kami.",
        date = "15 Apr 2025",
        status = "pending",
        isRead = true
    ),
    NotifikasiItem(
        id = "3",
        title = "Pengajuan Ditolak",
        message = "Maaf, pengajuan kamu sebagai pengelola wisata ditolak. Alasan: Data yang diberikan kurang lengkap. Silakan ajukan kembali.",
        date = "10 Apr 2025",
        status = "rejected",
        isRead = true
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotifikasiScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Notifikasi",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Primary,
                    titleContentColor = White,
                    navigationIconContentColor = White
                )
            )
        },
        containerColor = Background
    ) { paddingValues ->
        if (dummyNotifikasi.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🔔", style = MaterialTheme.typography.displayLarge)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        "Belum ada notifikasi",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = TextPrimary
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(dummyNotifikasi) { notif ->
                    NotifikasiCard(notif = notif)
                }
            }
        }
    }
}

@Composable
fun NotifikasiCard(notif: NotifikasiItem) {
    val statusColor = when (notif.status) {
        "approved" -> SuccessColor
        "rejected" -> ErrorColor
        else -> PendingColor
    }
    val statusIcon: ImageVector = when (notif.status) {
        "approved" -> Icons.Filled.CheckCircle
        "rejected" -> Icons.Filled.Cancel
        else -> Icons.Filled.HourglassEmpty
    }
    val statusLabel = when (notif.status) {
        "approved" -> "Disetujui"
        "rejected" -> "Ditolak"
        else -> "Menunggu"
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (!notif.isRead) White else White.copy(alpha = 0.7f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (!notif.isRead) 4.dp else 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Status icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(statusColor.copy(alpha = 0.12f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = statusIcon,
                    contentDescription = null,
                    tint = statusColor,
                    modifier = Modifier.size(26.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = notif.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = if (!notif.isRead) FontWeight.Bold else FontWeight.SemiBold,
                        color = TextPrimary
                    )
                    if (!notif.isRead) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(Primary, CircleShape)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = notif.message,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary,
                    maxLines = 3
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = notif.date,
                        style = MaterialTheme.typography.labelSmall,
                        color = TextSecondary
                    )
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = statusColor.copy(alpha = 0.12f)
                    ) {
                        Text(
                            text = statusLabel,
                            style = MaterialTheme.typography.labelSmall,
                            color = statusColor,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NotifikasiScreenPreview() {
    TravelWakaTheme {
        NotifikasiScreen(onBack = {})
    }
}
