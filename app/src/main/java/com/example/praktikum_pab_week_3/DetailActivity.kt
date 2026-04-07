package com.example.praktikum_pab_week_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class DetailActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val nama = intent.getStringExtra("NAMA_WISATA") ?: ""
        val harga = intent.getStringExtra("HARGA_TIKET") ?: ""
        val deskripsi = intent.getStringExtra("DESKRIPSI") ?: ""

        setContent {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Detail Wisata", fontWeight = FontWeight.Bold) },
                        navigationIcon = {
                            IconButton(onClick = { finish() }) { // Fungsi 'finish' untuk kembali
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "Back",
                                    tint = Color(0xFF01579B)
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                    )
                }
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.verticalGradient(listOf(Color(0xFFB3E5FC), Color.White)))
                        .padding(paddingValues)
                        .padding(20.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(24.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(24.dp)) {
                            Text(text = nama, fontSize = 28.sp, fontWeight = FontWeight.Black)
                            Text(text = "Informasi Lengkap", color = Color.Gray)
                            Divider(modifier = Modifier.padding(vertical = 16.dp))
                            Text(text = "Biaya Masuk: $harga", fontWeight = FontWeight.Bold, color = Color(0xFF0288D1))
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = deskripsi, lineHeight = 26.sp)
                        }
                    }
                }
            }
        }
    }
}