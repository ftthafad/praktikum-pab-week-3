package com.travelwaka.app.ui.screens.pengelola

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.travelwaka.app.ui.screens.profile.FormField
import com.travelwaka.app.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormWisataScreen(
    wisataId: String,
    onBack: () -> Unit,
    onSave: () -> Unit
) {
    val isEditMode = wisataId.isNotEmpty()

    var namaWisata by remember { mutableStateOf(if (isEditMode) "Candi Borobudur" else "") }
    var deskripsi by remember { mutableStateOf(if (isEditMode) "Candi Buddha terbesar di dunia yang terletak di Magelang, Jawa Tengah." else "") }
    var hargaTiket by remember { mutableStateOf(if (isEditMode) "50000" else "") }
    var jamBuka by remember { mutableStateOf(if (isEditMode) "08.00" else "") }
    var jamTutup by remember { mutableStateOf(if (isEditMode) "17.00" else "") }
    var latitude by remember { mutableStateOf(if (isEditMode) "-7.6079" else "") }
    var longitude by remember { mutableStateOf(if (isEditMode) "110.2038" else "") }
    var selectedCategory by remember { mutableStateOf(if (isEditMode) "Budaya" else "") }
    var expanded by remember { mutableStateOf(false) }

    val categories = listOf("Alam", "Budaya", "Pantai", "Kuliner", "Religi", "Petualangan")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (isEditMode) "Edit Wisata" else "Tambah Wisata",
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Upload Foto Section
            Text(
                text = "Foto Wisata",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(PrimaryLight.copy(alpha = 0.3f))
                    .border(2.dp, PrimaryMedium, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        Icons.Filled.AddPhotoAlternate,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Tap untuk upload foto",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Primary,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        "JPG/PNG, maks. 2MB per foto",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
            }

            HorizontalDivider(color = DividerColor)

            Text(
                text = "Informasi Wisata",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )

            FormField(
                value = namaWisata,
                onValueChange = { namaWisata = it },
                label = "Nama Wisata",
                icon = Icons.Filled.Landscape
            )

            // Dropdown Kategori
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = selectedCategory,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Kategori") },
                    leadingIcon = { Icon(Icons.Filled.Category, contentDescription = null, tint = Primary) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary,
                        focusedLabelColor = Primary
                    )
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(
                            text = { Text(category) },
                            onClick = {
                                selectedCategory = category
                                expanded = false
                            }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = deskripsi,
                onValueChange = { deskripsi = it },
                label = { Text("Deskripsi") },
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

            FormField(
                value = hargaTiket,
                onValueChange = { hargaTiket = it },
                label = "Harga Tiket (Rp)",
                icon = Icons.Filled.ConfirmationNumber
            )

            HorizontalDivider(color = DividerColor)
            Text(
                text = "Jam Operasional",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = jamBuka,
                    onValueChange = { jamBuka = it },
                    label = { Text("Jam Buka") },
                    leadingIcon = { Icon(Icons.Filled.AccessTime, contentDescription = null, tint = Primary) },
                    placeholder = { Text("08.00") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary,
                        focusedLabelColor = Primary,
                        cursorColor = Primary
                    ),
                    singleLine = true
                )
                OutlinedTextField(
                    value = jamTutup,
                    onValueChange = { jamTutup = it },
                    label = { Text("Jam Tutup") },
                    leadingIcon = { Icon(Icons.Filled.AccessTime, contentDescription = null, tint = Primary) },
                    placeholder = { Text("17.00") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary,
                        focusedLabelColor = Primary,
                        cursorColor = Primary
                    ),
                    singleLine = true
                )
            }

            HorizontalDivider(color = DividerColor)
            Text(
                text = "Koordinat Lokasi",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = latitude,
                    onValueChange = { latitude = it },
                    label = { Text("Latitude") },
                    leadingIcon = { Icon(Icons.Filled.MyLocation, contentDescription = null, tint = Primary) },
                    placeholder = { Text("-7.xxx") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary,
                        focusedLabelColor = Primary,
                        cursorColor = Primary
                    ),
                    singleLine = true
                )
                OutlinedTextField(
                    value = longitude,
                    onValueChange = { longitude = it },
                    label = { Text("Longitude") },
                    leadingIcon = { Icon(Icons.Filled.MyLocation, contentDescription = null, tint = Primary) },
                    placeholder = { Text("110.xxx") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Primary,
                        focusedLabelColor = Primary,
                        cursorColor = Primary
                    ),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onSave,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                enabled = namaWisata.isNotBlank() && selectedCategory.isNotBlank()
            ) {
                Icon(
                    imageVector = if (isEditMode) Icons.Filled.Save else Icons.Filled.Add,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    if (isEditMode) "Simpan Perubahan" else "Tambah Wisata",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormWisataScreenPreview() {
    TravelWakaTheme {
        FormWisataScreen(wisataId = "", onBack = {}, onSave = {})
    }
}

@Preview(showBackground = true)
@Composable
fun FormWisataEditScreenPreview() {
    TravelWakaTheme {
        FormWisataScreen(wisataId = "1", onBack = {}, onSave = {})
    }
}
