package com.example.ebiblioteka

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.database.FirebaseDatabase

@Composable
fun DodajKnjiguScreen(navController: NavHostController, imeKorisnika: String) {
    var naziv by remember { mutableStateOf("") }
    var autor by remember { mutableStateOf("") }
    var broj by remember { mutableStateOf("") }
    var jezik by remember { mutableStateOf("") }
    var tkoJeUnio by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ZajednickiHeader(imeKorisnika = imeKorisnika)
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Unesi naziv knjige:", fontWeight = FontWeight.Medium)
        TextField(
            value = naziv,
            onValueChange = { naziv = it },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFE0E0E0),
                unfocusedContainerColor = Color(0xFFE0E0E0),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(15.dp)
        )

        Text(text = "Autor knjige:", fontWeight = FontWeight.Medium)
        TextField(
            value = autor,
            onValueChange = { autor = it },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFE0E0E0),
                unfocusedContainerColor = Color(0xFFE0E0E0),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(15.dp)
        )

        Text(text = "Broj:", fontWeight = FontWeight.Medium)
        TextField(
            value = broj,
            onValueChange = { broj = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.width(100.dp).padding(vertical = 4.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFE0E0E0),
                unfocusedContainerColor = Color(0xFFE0E0E0),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(15.dp)
        )

        Text(text = "Jezik:", fontWeight = FontWeight.Medium)
        TextField(
            value = jezik,
            onValueChange = { jezik = it },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFE0E0E0),
                unfocusedContainerColor = Color(0xFFE0E0E0),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(15.dp)
        )

        Text(text = "Ime ko je unio knjigu:", fontWeight = FontWeight.Medium)
        TextField(
            value = tkoJeUnio,
            onValueChange = { tkoJeUnio = it },
            modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFE0E0E0),
                unfocusedContainerColor = Color(0xFFE0E0E0),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(15.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp), contentAlignment = Alignment.Center) {
            Button(
                onClick = {
                    val url = "https://ebiblioteka-28abf-default-rtdb.europe-west1.firebasedatabase.app"
                    val ref = FirebaseDatabase.getInstance(url).reference.push()

                    val novaKnjigaMape = mapOf(
                        "naslov" to naziv,
                        "autori" to autor,
                        "broj" to (broj.toIntOrNull() ?: 0),
                        "jezik" to jezik,
                        "tkoJeUnio" to tkoJeUnio,
                        "žanr" to "Općenito"
                    )
                    ref.setValue(novaKnjigaMape)
                    navController.navigate("lista/Sve")
                },
                colors = ButtonDefaults.buttonColors(containerColor = PrimarnaBoja),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth(0.6f).height(48.dp)
            ) {
                Text("Dodaj knjigu", fontSize = 16.sp, color = Color.White)
            }
        }
    }
}