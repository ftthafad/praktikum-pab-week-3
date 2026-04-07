package com.example.praktikum_pab_week_3

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(modifier = Modifier.fillMaxSize()) {
                TourismProfileScreen()
            }
        }
    }
}

@Composable
fun TourismProfileScreen() {
    val context = LocalContext.current
    val scrollState = rememberScrollState() // Untuk fitur Scrolling

    // Data
    val namaWisata = "Candi Borobudur"
    val hargaTiket = "Rp 50.000"
    val deskripsi = "Candi Buddha terbesar di dunia yang terletak di Magelang, Jawa Tengah. " +
            "Ditetapkan sebagai Situs Warisan Dunia UNESCO, tempat ini menawarkan " +
            "pemandangan matahari terbit yang menakjubkan dan arsitektur kuno yang megah."

    // Background Gradasi Biru ke Putih
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(Color(0xFFB3E5FC), Color.White)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
            .verticalScroll(scrollState) // Mengaktifkan Scroll
            .padding(20.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "DESTINASI WISATA",
                style = MaterialTheme.typography.labelLarge,
                color = Color(0xFF0288D1),
                fontWeight = FontWeight.Bold
            )

            // 1. Gambar dengan Sudut Tumpul (Rounded)
            Image(
                painter = painterResource(id = R.drawable.borobudur),
                contentDescription = "Foto Borobudur",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(24.dp)), // Sudut tumpul
                contentScale = ContentScale.Crop
            )

            // 2. Card View Modern (Flexible Size)
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp) // Efek Bayangan
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = namaWisata,
                        fontSize = 26.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF01579B)
                    )

                    Surface(
                        color = Color(0xFFE1F5FE),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Tiket: $hargaTiket",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            color = Color(0xFF0288D1),
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Text(
                        text = deskripsi,
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = 24.sp,
                        color = Color.DarkGray
                    )
                }
            }

            // 3. Tombol-Tombol Modern
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = {
                        val gmmIntentUri = Uri.parse("geo:0,0?q=Candi+Borobudur")
                        context.startActivity(Intent(Intent.ACTION_VIEW, gmmIntentUri))
                    },
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0288D1))
                ) {
                    Text("📍 Buka Lokasi di Peta", fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = {
                        val shareIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "Ayo ke $namaWisata! Harga cuma $hargaTiket.")
                            type = "text/plain"
                        }
                        context.startActivity(Intent.createChooser(shareIntent, null))
                    },
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    shape = RoundedCornerShape(16.dp),
                    border = ButtonDefaults.outlinedButtonBorder.copy(width = 2.dp)
                ) {
                    Text("📤 Share ke Teman", color = Color(0xFF0288D1), fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = {
                        val intent = Intent(context, DetailActivity::class.java).apply {
                            putExtra("NAMA_WISATA", namaWisata)
                            putExtra("HARGA_TIKET", hargaTiket)
                            putExtra("DESKRIPSI", deskripsi)
                        }
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF01579B))
                ) {
                    Text("Lihat Detail Lengkap", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}