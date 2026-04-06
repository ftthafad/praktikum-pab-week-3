package com.example.praktikum_pab_week_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Mengambil data yang dikirim dari MainActivity
        val nama = intent.getStringExtra("NAMA_WISATA") ?: ""
        val harga = intent.getStringExtra("HARGA_TIKET") ?: ""
        val deskripsi = intent.getStringExtra("DESKRIPSI") ?: ""

        setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Detail Wisata", style = MaterialTheme.typography.headlineMedium)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Nama: $nama", style = MaterialTheme.typography.titleLarge)
                    Text(text = "Harga: $harga")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Deskripsi Lengkap:")
                    Text(text = deskripsi)
                }
            }
        }
    }
}