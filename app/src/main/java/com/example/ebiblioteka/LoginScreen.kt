package com.example.ebiblioteka

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(navController: NavHostController) {
    var korisnickoIme by remember { mutableStateOf("") }
    var sifra by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Upiši Se!", fontSize = 28.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 32.dp))

        Text(text = "Korisničko Ime", color = PrimarnaBoja, fontWeight = FontWeight.Medium)
        TextField(
            value = korisnickoIme,
            onValueChange = { korisnickoIme = it },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF2F2F7),
                unfocusedContainerColor = Color(0xFFF2F2F7),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Šifra", color = PrimarnaBoja, fontWeight = FontWeight.Medium)
        TextField(
            value = sifra,
            onValueChange = { sifra = it },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFFF2F2F7),
                unfocusedContainerColor = Color(0xFFF2F2F7),
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (korisnickoIme.isNotBlank()) {
                    navController.navigate("home/$korisnickoIme")
                } else {
                    navController.navigate("home/Korisnik")
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = PrimarnaBoja),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text(text = "Prijavi se", color = Color.White, fontSize = 16.sp)
        }
    }
}