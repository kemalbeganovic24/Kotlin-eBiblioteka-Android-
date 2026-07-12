package com.example.ebiblioteka

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

    LaunchedEffect(odabranaVrsta) {
        val url = "https://ebiblioteka-28abf-default-rtdb.europe-west1.firebasedatabase.app"
        val ref = FirebaseDatabase.getInstance(url).reference

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val ucitaneKnjige = mutableListOf<Knjiga>()

                for (child in snapshot.children) {
                    try {
                        val naslov = child.child("naslov").value?.toString() ?: "Nepoznat naslov"
                        val jezik = child.child("jezik").value?.toString() ?: "BHS"
                        val tkoJeUnio = child.child("tkoJeUnio").value?.toString() ?: "Sistem"

                        val vrsta = child.child("žanr").value?.toString()
                            ?: (child.child("vrsta").value?.toString() ?: "Općenito")

                        val brojRaw = child.child("broj").value
                        val broj = when (brojRaw) {
                            is Long -> brojRaw.toInt()
                            is Int -> brojRaw
                            is String -> brojRaw.toIntOrNull() ?: 0
                            is Double -> brojRaw.toInt()
                            else -> 0
                        }

                        val autoriRaw = child.child("autori").value
                        val autorIme = when (autoriRaw) {
                            is String -> autoriRaw
                            is List<*> -> autoriRaw.filterIsInstance<String>().joinToString(", ")
                            else -> child.child("autor").value?.toString() ?: "Nepoznat autor"
                        }

                        val novaKnjiga = Knjiga(
                            id = child.key ?: "",
                            naslov = naslov,
                            autor = autorIme,
                            broj = broj,
                            jezik = jezik,
                            vrsta = vrsta,
                            tkoJeUnio = tkoJeUnio,
                            naStanju = broj > 0
                        )

                        if (
                            odabranaVrsta == "Sve" ||
                            vrsta.contains(odabranaVrsta, ignoreCase = true)
                        ) {
                            ucitaneKnjige.add(novaKnjiga)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
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
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = knjiga.naslov, fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
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
                        Text(
                            text = "Broj: ${knjiga.broj}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )

                        Text(
                            text = "Jezik: ${knjiga.jezik}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )

                        Text(
                            text = "Unio: ${knjiga.tkoJeUnio}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )

                        Text(
                            text = "Žanr: ${knjiga.vrsta}",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        HorizontalDivider(modifier = Modifier.padding(top = 8.dp), color = Color(0xFFE0E0E0))
                    }
                }
            }
        }
    }
}