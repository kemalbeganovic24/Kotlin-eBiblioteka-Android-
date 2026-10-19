package com.example.ebiblioteka

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
fun SearchScreen(imeKorisnika: String) {
    var upitZaPretragu by remember { mutableStateOf("") }
    var sveKnjige by remember { mutableStateOf(listOf<Knjiga>()) }
    var odabranaKnjiga by remember { mutableStateOf<Knjiga?>(null) }

    LaunchedEffect(Unit) {
        val url = "https://ebiblioteka-28abf-default-rtdb.europe-west1.firebasedatabase.app"
        FirebaseDatabase.getInstance(url).reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                sveKnjige = snapshot.children.map { it.toKnjiga() }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    val filtriraneKnjige = sveKnjige.filter {
        it.naslov.contains(upitZaPretragu, ignoreCase = true) ||
                it.autor.contains(upitZaPretragu, ignoreCase = true)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        ZajednickiHeader(imeKorisnika = imeKorisnika)
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = upitZaPretragu,
            onValueChange = { upitZaPretragu = it },
            placeholder = { Text("Pretraži knjigu...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFE0E0E0),
                unfocusedContainerColor = Color(0xFFE0E0E0),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(15.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(filtriraneKnjige) { knjiga ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { odabranaKnjiga = knjiga },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = knjiga.naslov,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("Autor: ${knjiga.autor}")
                        Text("Količina: ${knjiga.broj}")
                        Text("Jezik: ${knjiga.jezik}")
                        Text("Unio: ${knjiga.tkoJeUnio}")
                        Text(
                            text = if (knjiga.naStanju) "DOSTUPNO" else "NEDOSTUPNO",
                            color = if (knjiga.naStanju) Color(0xFF2E7D32) else Color.Red
                        )
                    }
                }
            }
        }
    }

    // Prozor sa detaljima, ide na sam kraj funkcije
    odabranaKnjiga?.let { knjiga ->
        KnjigaDetaljiSheet(knjiga = knjiga, onDismiss = { odabranaKnjiga = null })
    }
}