package com.example.ebiblioteka

data class Knjiga(
    val id: String = "",
    val naslov: String = "",
    val autor: String = "",
    val broj: Int = 0,
    val jezik: String = "BHS",
    val vrsta: String = "Općenito",
    val tkoJeUnio: String = "",
    val naStanju: Boolean = true
)