package com.travelwaka.app.ui.screens.superadmin

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.travelwaka.app.ui.theme.*

data class PengajuanItem(
    val id: String,
    val namaUser: String,
    val email: String,
    val namaUsaha: String,
    val deskripsi: String,
    val alasan: String,
    val tanggal: String,
    val status: String = "pending" // "pending", "approved", "rejected"
)

val dummyPengajuan = listOf(
    PengajuanItem(
        id = "1",
        namaUser = "Budi Santoso",
        email = "budi@email.com",
        namaUsaha = "Warung Makan Sari Rasa",
        deskripsi = "Restoran masakan Jawa dengan menu lengkap dan harga terjangkau.",
        alasan = "Ingin memperkenalkan kuliner khas Jawa kepada wisatawan.",
        tanggal = "17 Apr 2025"
    ),
    PengajuanItem(
        id = "2",
        namaUser = "Dewi Kartika",
        email = "dewi@email.com",
        namaUsaha = "Homestay Bukit Indah",
        deskripsi = "Penginapan dengan pemandangan bukit yang indah di Wonosobo.",
        alasan = "Ingin membantu wisatawan mendapatkan tempat menginap yang nyaman.",
        tanggal = "16 Apr 2025"
    ),
    PengajuanItem(
        id = "3",
        namaUser = "Ahmad Rizki",
        email = "ahmad@email.com",
        namaUsaha = "Taman Agrowisata Kemuning",
        deskripsi = "Wisata perkebunan teh dengan pengalaman petik teh langsung.",
        alasan = "Ingin mempromosikan agrowisata teh kepada pengunjung dari luar daerah.",
        tanggal = "15 Apr 2025"
    ),
    PengajuanItem(
        id = "4",
        namaUser = "Siti Aminah",
        email = "siti@email.com",
        namaUsaha = "Galeri Batik Laweyan",
        deskripsi = "Galeri dan workshop batik tulis khas Solo yang sudah berdiri sejak 1985.",
        alasan = "Ingin melestarikan batik tulis dan menarik wisatawan ke kawasan Laweyan.",
        tanggal = "14 Apr 2025"
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardApprovalScreen() {
    var pengajuanList by remember { mutableStateOf(dummyPengajuan.toMutableList()) }
    var selectedTab by remember { mutableStateOf(0) }
    var detailItem by remember { mutableStateOf<PengajuanItem?>(null) }

    val tabs = listOf("Semua", "Pending", "Disetujui", "Ditolak")
    val filteredList = when (selectedTab) {
        1 -> pengajuanList.filter { it.status == "pending" }
        2 -> pengajuanList.filter { it.status == "approved" }
        3 -> pengajuanList.filter { it.status == "rejected" }
        else -> pengajuanList
    }

    // Detail bottom sheet / dialog
    detailItem?.let { item ->
        AlertDialog(
            onDismissRequest = { detailItem = null },
            title = {
                Text(
                    "Detail Pengajuan",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    DetailRow(label = "Nama", value = item.namaUser)
                    DetailRow(label = "Email", value = item.email)
                    DetailRow(label = "Nama Usaha", value = item.namaUsaha)
                    DetailRow(label = "Deskripsi", value = item.deskripsi)
                    DetailRow(label = "Alasan", value = item.alasan)
                    DetailRow(label = "Tanggal", value = item.tanggal)
                }
            },
            confirmButton = {
                if (item.status == "pending") {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = {
                                pengajuanList = pengajuanList.map {
                                    if (it.id == item.id) it.copy(status = "approved") else it
                                }.toMutableList()
                                detailItem = null
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = SuccessColor),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Setujui")
                        }
                        Button(
                            onClick = {
                                pengajuanList = pengajuanList.map {
                                    if (it.id == item.id) it.copy(status = "rejected") else it
                                }.toMutableList()
                                detailItem = null
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = ErrorColor),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(Icons.Filled.Close, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Tolak")
                        }
                    }
                } else {
                    TextButton(onClick = { detailItem = null }) { Text("Tutup") }
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            "Dashboard Admin",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "Approval Pengelola Wisata",
                            style = MaterialTheme.typography.bodySmall,
                            color = PrimaryLight
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Primary,
                    titleContentColor = White
                )
            )
        },
        containerColor = Background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Stats Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Primary)
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(
                    label = "Pending",
                    count = pengajuanList.count { it.status == "pending" }.toString(),
                    color = PendingColor,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    label = "Disetujui",
                    count = pengajuanList.count { it.status == "approved" }.toString(),
                    color = SuccessColor,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    label = "Ditolak",
                    count = pengajuanList.count { it.status == "rejected" }.toString(),
                    color = ErrorColor,
                    modifier = Modifier.weight(1f)
                )
            }

            // Tabs
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                containerColor = White,
                contentColor = Primary,
                edgePadding = 0.dp
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                title,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    )
                }
            }

            // List Pengajuan
            if (filteredList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("📋", style = MaterialTheme.typography.displayLarge)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Tidak ada pengajuan",
                            style = MaterialTheme.typography.titleMedium,
                            color = TextSecondary
                        )
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredList) { item ->
                        ApprovalCard(
                            item = item,
                            onApprove = {
                                pengajuanList = pengajuanList.map {
                                    if (it.id == item.id) it.copy(status = "approved") else it
                                }.toMutableList()
                            },
                            onReject = {
                                pengajuanList = pengajuanList.map {
                                    if (it.id == item.id) it.copy(status = "rejected") else it
                                }.toMutableList()
                            },
                            onDetail = { detailItem = item }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(
    label: String,
    count: String,
    color: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = White.copy(alpha = 0.15f))
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = count,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = White
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = White.copy(alpha = 0.8f)
            )
        }
    }
}

@Composable
fun ApprovalCard(
    item: PengajuanItem,
    onApprove: () -> Unit,
    onReject: () -> Unit,
    onDetail: () -> Unit
) {
    val statusColor = when (item.status) {
        "approved" -> SuccessColor
        "rejected" -> ErrorColor
        else -> PendingColor
    }
    val statusLabel = when (item.status) {
        "approved" -> "Disetujui"
        "rejected" -> "Ditolak"
        else -> "Menunggu"
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(Primary, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = item.namaUser.first().toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        item.namaUser,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary
                    )
                    Text(item.email, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
                }
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = statusColor.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = statusLabel,
                        style = MaterialTheme.typography.labelSmall,
                        color = statusColor,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = DividerColor)
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = item.namaUsaha,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = Primary
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.deskripsi,
                style = MaterialTheme.typography.bodySmall,
                color = TextSecondary,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.CalendarToday, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text(item.tanggal, style = MaterialTheme.typography.labelSmall, color = TextSecondary)
            }

            if (item.status == "pending") {
                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onDetail,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Primary),
                        border = androidx.compose.foundation.BorderStroke(1.5.dp, Primary)
                    ) {
                        Text("Detail", style = MaterialTheme.typography.labelLarge)
                    }
                    Button(
                        onClick = onReject,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ErrorColor)
                    ) {
                        Icon(Icons.Filled.Close, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Tolak", style = MaterialTheme.typography.labelLarge)
                    }
                    Button(
                        onClick = onApprove,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SuccessColor)
                    ) {
                        Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Setuju", style = MaterialTheme.typography.labelLarge)
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(onClick = onDetail, contentPadding = PaddingValues(0.dp)) {
                    Text("Lihat Detail", color = Primary, style = MaterialTheme.typography.labelLarge)
                }
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Column {
        Text(label, style = MaterialTheme.typography.labelMedium, color = TextSecondary, fontWeight = FontWeight.SemiBold)
        Text(value, style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardApprovalScreenPreview() {
    TravelWakaTheme {
        DashboardApprovalScreen()
    }
}
