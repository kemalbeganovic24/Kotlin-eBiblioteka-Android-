package com.example.ebiblioteka

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Composable
fun ListaKnjigaScreen(odabranaVrsta: String, imeKorisnika: String) {
    var knjige by remember { mutableStateOf(listOf<Knjiga>()) }
    var odabranaKnjiga by remember { mutableStateOf<Knjiga?>(null) }

    LaunchedEffect(odabranaVrsta) {
        val url = "https://ebiblioteka-28abf-default-rtdb.europe-west1.firebasedatabase.app"
        val ref = FirebaseDatabase.getInstance(url).reference

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val ucitaneKnjige = mutableListOf<Knjiga>()

                for (child in snapshot.children) {
                    val knjiga = child.toKnjiga()
                    if (odabranaVrsta == "Sve" ||
                        knjiga.vrsta.contains(odabranaVrsta, ignoreCase = true)
                    ) {
                        ucitaneKnjige.add(knjiga)
                    }
                }
                knjige = ucitaneKnjige.sortedBy { it.naslov.lowercase() }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        ZajednickiHeader(imeKorisnika = imeKorisnika)
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Kategorija: $odabranaVrsta",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = PrimarnaBoja,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (knjige.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Nema dostupnih knjiga u ovoj kategoriji.", color = Color.Gray)
            }
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(knjige) { knjiga ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { odabranaKnjiga = knjiga }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = knjiga.naslov,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = if (knjiga.broj > 0) Color(0xFFC8E6C9) else Color(0xFFFFCDD2)
                                ),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = if (knjiga.broj > 0) "DOSTUPNO" else "NIJE DOSTUPNO",
                                    color = if (knjiga.broj > 0) Color(0xFF2E7D32) else Color(0xFFC62828),
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                )
                            }
                        }
                        Text(text = "Autor: ${knjiga.autor}", fontSize = 14.sp, color = Color.Gray)
                        Text(text = "Broj: ${knjiga.broj}", fontSize = 12.sp, color = Color.Gray)
                        Text(text = "Jezik: ${knjiga.jezik}", fontSize = 12.sp, color = Color.Gray)
                        Text(text = "Unio: ${knjiga.tkoJeUnio}", fontSize = 12.sp, color = Color.Gray)
                        Text(text = "Žanr: ${knjiga.vrsta}", fontSize = 12.sp, color = Color.Gray)
                        HorizontalDivider(modifier = Modifier.padding(top = 8.dp), color = Color(0xFFE0E0E0))
                    }
                }
            }
        }
    }

    odabranaKnjiga?.let { knjiga ->
        KnjigaDetaljiSheet(knjiga = knjiga, onDismiss = { odabranaKnjiga = null })
    }
}