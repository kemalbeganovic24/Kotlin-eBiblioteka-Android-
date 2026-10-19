package com.example.ebiblioteka

import com.google.firebase.database.DataSnapshot

data class Knjiga(
    val id: String = "",
    val naslov: String = "",
    val autor: String = "",
    val broj: Int = 0,
    val jezik: String = "BHS",
    val vrsta: String = "Općenito",
    val tkoJeUnio: String = "",
    val naStanju: Boolean = true,
    val isbn: String = "",
    val izdavac: String = "",
    val slika: String = ""
)

fun DataSnapshot.toKnjiga(): Knjiga {
    // vraća prvo neprazno polje od ponuđenih ključeva
    fun tekst(vararg kljucevi: String): String {
        for (k in kljucevi) {
            val v = child(k).value?.toString()?.trim()
            if (!v.isNullOrEmpty()) return v
        }
        return ""
    }

    val broj = when (val b = child("broj").value) {
        is Long -> b.toInt()
        is Int -> b
        is Double -> b.toInt()
        is String -> b.toIntOrNull() ?: 0
        else -> 0
    }

    val isbn = tekst("isbn").let { if (it.startsWith("Nije dodijeljen")) "" else it }

    return Knjiga(
        id = key ?: "",
        naslov = tekst("naslov").ifEmpty { "Nepoznat naslov" },
        autor = tekst("autori", "autor").ifEmpty { "Nepoznat autor" },
        broj = broj,
        jezik = tekst("jezik", "jezici").ifEmpty { "BHS" },
        vrsta = tekst("žanr", "vrsta").ifEmpty { "Općenito" },
        tkoJeUnio = tekst("tkoJeUnio").ifEmpty { "Sistem" },
        naStanju = broj > 0,
        isbn = isbn,
        izdavac = tekst("izdavac", "izdavači"),
        slika = tekst("cover")
    )
}