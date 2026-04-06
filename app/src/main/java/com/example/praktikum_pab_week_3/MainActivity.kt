package com.example.praktikum_pab_week_3

import android.os.Bundle
import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Kita pakai Surface standar saja tanpa tema khusus project dulu
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                TourismProfileScreen()
            }
        }
    }
}

@Composable
fun TourismProfileScreen() {
    val context = LocalContext.current

    // Data yang akan ditampilkan & dikirim
    val namaWisata = "Candi Borobudur"
    val hargaTiket = "Rp 50.000"
    val deskripsi = "Candi Buddha terbesar di dunia yang terletak di Magelang."

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 1. Gambar Destinasi
        // Jika masih error di sini, pastikan file borobudur.png ada di res/drawable
        Image(
            painter = painterResource(id = R.drawable.borobudur),
            contentDescription = "Foto Borobudur",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        // 2. Judul dan Deskripsi
        Text(text = namaWisata, style = MaterialTheme.typography.headlineLarge)
        Text(text = "Harga Tiket: $hargaTiket", style = MaterialTheme.typography.titleMedium)
        Text(text = deskripsi, style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // 3. Tombol Buka Maps (Implicit Intent)
        Button(
            onClick = {
                val gmmIntentUri = Uri.parse("geo:0,0?q=Candi+Borobudur")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                context.startActivity(mapIntent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Buka Peta")
        }

        // 4. Tombol Share (Implicit Intent)
        Button(
            onClick = {
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Kunjungi $namaWisata! Tiket cuma $hargaTiket. $deskripsi")
                    type = "text/plain"
                }
                context.startActivity(Intent.createChooser(shareIntent, null))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Share Info Wisata")
        }

        // 5. Tombol Detail (Explicit Intent - Sementara masih kosong)
        // 5. Tombol Detail (Explicit Intent)
        Button(
            onClick = {
                val intent = Intent(context, DetailActivity::class.java).apply {
                    putExtra("NAMA_WISATA", namaWisata)
                    putExtra("HARGA_TIKET", hargaTiket)
                    putExtra("DESKRIPSI", deskripsi)
                }
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Lihat Detail Lengkap")
        }
    }
}