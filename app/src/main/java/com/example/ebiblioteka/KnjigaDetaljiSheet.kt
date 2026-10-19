package com.example.ebiblioteka

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KnjigaDetaljiSheet(knjiga: Knjiga, onDismiss: () -> Unit) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val naslovBoja = Color(0xFFD2723A)
    val dostupno = knjiga.broj > 0

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color(0xFFF2F2F7)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)) {
                Text(
                    text = "Detalji o knjizi",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )

            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (knjiga.slika.isNotBlank()) {
                        AsyncImage(
                            model = knjiga.slika,
                            contentDescription = knjiga.naslov,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .height(240.dp)
                                .shadow(6.dp)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                    }

                    Text(
                        text = knjiga.naslov,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = naslovBoja,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Autori: ${knjiga.autor}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (knjiga.isbn.isNotBlank()) DetaljRed("ISBN", knjiga.isbn)
                        DetaljRed("Broj Knjiga Dostupno", knjiga.broj.toString())
                        if (knjiga.izdavac.isNotBlank()) DetaljRed("Izdavač", knjiga.izdavac)
                        DetaljRed("Jezik", knjiga.jezik)
                        DetaljRed("Žanr", knjiga.vrsta)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {},
                        enabled = false,
                        modifier = Modifier.fillMaxWidth().height(54.dp),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(
                            disabledContainerColor = if (dostupno) Color(0xFF69D472) else Color(0xFFEF9A9A),
                            disabledContentColor = Color.White
                        )
                    ) {
                        Text(
                            text = if (dostupno) "DOSTUPNO" else "NIJE DOSTUPNO",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DetaljRed(labela: String, vrijednost: String) {
    Text(
        text = "$labela: $vrijednost",
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        color = Color.Gray,
        modifier = Modifier.fillMaxWidth()
    )
}