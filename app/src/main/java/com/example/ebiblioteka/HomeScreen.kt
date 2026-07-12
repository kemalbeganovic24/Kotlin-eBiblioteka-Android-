package com.example.ebiblioteka

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HomeScreen(navController: NavHostController, imeKorisnika: String) {
    val vrsteKnjiga = listOf("Poezija", "Proza", "Drama", "Historija / Dokumentaristika","Pripovijetke / Novele" ,"Roman","Eseji i književna kritika","Lingvistika / Pravopis","Časopis / Godišnjak" ,"Monografija", "Putopis", "Biografija / Memoari","Zbirke radova", "Usmena književnost", "Enciklopedija")

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        ZajednickiHeader(imeKorisnika = imeKorisnika)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Vrsta", fontSize = 14.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF2F2F7), shape = RoundedCornerShape(10.dp))
                .clickable { navController.navigate("lista/Sve") }
                .padding(12.dp)
        ) {
            Text(text = "Izaberite vrstu knjige", color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(vrsteKnjiga) { vrsta ->
                Text(
                    text = vrsta,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val enkodiranaVrsta = URLEncoder.encode(vrsta, StandardCharsets.UTF_8.toString())
                            navController.navigate("lista/$enkodiranaVrsta")
                        }
                        .padding(vertical = 8.dp)
                )
                HorizontalDivider(color = Color(0xFFE0E0E0))
            }
        }
    }
}