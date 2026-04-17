package com.travelwaka.app.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormPengajuanScreen(
    onBack: () -> Unit,
    onSubmit: () -> Unit
) {
    var namaUsaha by remember { mutableStateOf("") }
    var deskripsi by remember { mutableStateOf("") }
    var alamat by remember { mutableStateOf("") }
    var noTelepon by remember { mutableStateOf("") }
    var alasan by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            icon = { Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = SuccessColor, modifier = Modifier.size(48.dp)) },
            title = {
                Text("Pengajuan Terkirim!", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            },
            text = {
                Text(
                    "Pengajuan kamu sebagai pengelola wisata sedang diproses. Kami akan memberitahu kamu melalui notifikasi.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            },
            confirmButton = {
                Button(
                    onClick = { showDialog = false; onSubmit() },
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) { Text("OK") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Ajukan Jadi Pengelola", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Info banner
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = PrimaryLight.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(Icons.Filled.Info, contentDescription = null, tint = Primary, modifier = Modifier.size(20.dp))
                    Text(
                        text = "Pengajuan akan ditinjau oleh admin. Proses verifikasi membutuhkan 1-3 hari kerja.",
                        style = MaterialTheme.typography.bodySmall,
                        color = Primary
                    )
                }
            }

            FormField(
                value = namaUsaha,
                onValueChange = { namaUsaha = it },
                label = "Nama Usaha / Tempat Wisata",
                icon = Icons.Filled.Store
            )
            FormField(
                value = alamat,
                onValueChange = { alamat = it },
                label = "Alamat Lengkap",
                icon = Icons.Filled.LocationOn
            )
            FormField(
                value = noTelepon,
                onValueChange = { noTelepon = it },
                label = "Nomor Telepon",
                icon = Icons.Filled.Phone
            )
            OutlinedTextField(
                value = deskripsi,
                onValueChange = { deskripsi = it },
                label = { Text("Deskripsi Tempat Wisata") },
                leadingIcon = { Icon(Icons.Filled.Description, contentDescription = null, tint = Primary) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary,
                    focusedLabelColor = Primary,
                    cursorColor = Primary
                ),
                maxLines = 4
            )
            OutlinedTextField(
                value = alasan,
                onValueChange = { alasan = it },
                label = { Text("Alasan Mengajukan") },
                leadingIcon = { Icon(Icons.Filled.Edit, contentDescription = null, tint = Primary) },
                placeholder = { Text("Jelaskan mengapa kamu ingin menjadi pengelola wisata...") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary,
                    focusedLabelColor = Primary,
                    cursorColor = Primary
                ),
                maxLines = 4
            )

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { showDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                enabled = namaUsaha.isNotBlank() && deskripsi.isNotBlank() && alasan.isNotBlank()
            ) {
                Text("Kirim Pengajuan", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun FormField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = { Icon(icon, contentDescription = null, tint = Primary) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            cursorColor = Primary
        ),
        singleLine = true
    )
}

@Preview(showBackground = true)
@Composable
fun FormPengajuanScreenPreview() {
    TravelWakaTheme {
        FormPengajuanScreen(onBack = {}, onSubmit = {})
    }
}
