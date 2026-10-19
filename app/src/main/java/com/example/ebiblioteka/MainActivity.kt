package com.example.ebiblioteka

import android.os.Bundle
import com.google.firebase.FirebaseApp
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

val PrimarnaBoja = Color(0xFFD68A1A)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            MaterialTheme {
                MainAppNavigation()
            }
        }
    }
}

@Composable
fun MainAppNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val prikaziBottomBar = currentRoute != null &&
            (currentRoute.startsWith("home") ||
                    currentRoute.startsWith("lista") ||
                    currentRoute.startsWith("dodaj") ||
                    currentRoute.startsWith("search"))

    var trenutniKorisnik by remember { mutableStateOf("Gost") }

    Scaffold(
        bottomBar = {
            if (prikaziBottomBar) {
                NavigationBar(modifier = Modifier.background(Color.White)) {
                    NavigationBarItem(
                        selected = currentRoute?.startsWith("home") == true,
                        onClick = {
                            if (currentRoute?.startsWith("home") != true) {
                                navController.navigate("home/$trenutniKorisnik") {
                                    popUpTo("home/$trenutniKorisnik") { inclusive = false }
                                    launchSingleTop = true
                                }
                            }
                        },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        colors = NavigationBarItemDefaults.colors(selectedIconColor = PrimarnaBoja, indicatorColor = Color(0xFFF2F2F7))
                    )
                    NavigationBarItem(
                        selected = currentRoute?.startsWith("lista") == true,
                        onClick = {
                            if (currentRoute?.startsWith("lista/Sve") != true) {
                                navController.navigate("lista/Sve") {
                                    launchSingleTop = true
                                }
                            }
                        },
                        icon = { Icon(Icons.Default.List, contentDescription = "Lista") },
                        colors = NavigationBarItemDefaults.colors(selectedIconColor = PrimarnaBoja, indicatorColor = Color(0xFFF2F2F7))
                    )
                    NavigationBarItem(
                        selected = currentRoute?.startsWith("dodaj") == true,
                        onClick = {
                            if (currentRoute != "dodaj") {
                                navController.navigate("dodaj") {
                                    launchSingleTop = true
                                }
                            }
                        },
                        icon = { Icon(Icons.Default.Add, contentDescription = "Dodaj") },
                        colors = NavigationBarItemDefaults.colors(selectedIconColor = PrimarnaBoja, indicatorColor = Color(0xFFF2F2F7))
                    )
                    NavigationBarItem(
                        selected = currentRoute?.startsWith("search") == true,
                        onClick = {
                            if (currentRoute != "search") {
                                navController.navigate("search") {
                                    launchSingleTop = true
                                }
                            }
                        },
                        icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                        colors = NavigationBarItemDefaults.colors(selectedIconColor = PrimarnaBoja, indicatorColor = Color(0xFFF2F2F7))
                    )
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            NavHost(
                navController = navController,
                startDestination = "onboarding"
            ) {
                composable("onboarding") { OnboardingScreen(navController) }


                composable(
                    route = "home/{korisnik}",
                    arguments = listOf(navArgument("korisnik") { type = NavType.StringType })
                ) { backStackEntry ->
                    val korisnik = backStackEntry.arguments?.getString("korisnik") ?: "Korisnik"
                    trenutniKorisnik = korisnik
                    HomeScreen(navController, korisnik)
                }

                composable(
                    route = "lista/{vrsta}",
                    arguments = listOf(navArgument("vrsta") { type = NavType.StringType })
                ) { backStackEntry ->
                    val vrsta = backStackEntry.arguments?.getString("vrsta") ?: "Sve"

                    val dekodiranaVrsta = try {
                        URLDecoder.decode(vrsta, StandardCharsets.UTF_8.toString())
                    } catch (e: Exception) {
                        vrsta
                    }

                    ListaKnjigaScreen(odabranaVrsta = dekodiranaVrsta, imeKorisnika = trenutniKorisnik)
                }

                composable("dodaj") {
                    DodajKnjiguScreen(navController, trenutniKorisnik)
                }

                composable("search") {
                    SearchScreen(trenutniKorisnik)
                }
            }
        }
    }
}

@Composable
fun ZajednickiHeader(imeKorisnika: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.bzk),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "E-Biblioteka", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
        Text(text = imeKorisnika, fontSize = 14.sp, color = Color.Gray)
    }
}