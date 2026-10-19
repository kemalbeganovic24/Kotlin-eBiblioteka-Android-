# eBiblioteka (Android)

Android aplikacija za pregled i upravljanje bibliotečkim fondom BZK Preporod. Napisana u **Kotlinu** uz **Jetpack Compose**, a podaci se čitaju i upisuju u **Firebase Realtime Database**.

## Mogućnosti

- **Početni ekran (onboarding)** sa dugmetom "Započni" koje vodi direktno na glavnu stranicu
- **Kategorije knjiga** (poezija, proza, drama, historija, enciklopedija i dr.)
- **Lista knjiga** po izabranoj kategoriji, sortirana po naslovu, sa oznakom dostupnosti
- **Detalji o knjizi**: klik na knjigu otvara prozor sa koricama, autorima, ISBN-om, izdavačem, jezikom, žanrom i dostupnošću
- **Pretraga** po naslovu i autoru
- **Dodavanje nove knjige** direktno u bazu
- Podaci se osvježavaju u realnom vremenu

## Tehnologije

| Dio | Tehnologija |
|---|---|
| Jezik | Kotlin |
| UI | Jetpack Compose, Material 3 |
| Navigacija | Navigation Compose |
| Baza | Firebase Realtime Database |
| Slike (korice) | Coil |

## Struktura projekta

```
app/src/main/java/com/example/ebiblioteka/
├── MainActivity.kt          # Navigacija, donja traka, zajednički header
├── OnboardingScreen.kt      # Uvodni ekran
├── HomeScreen.kt            # Izbor kategorije
├── ListaKnjigaScreen.kt     # Lista knjiga po kategoriji
├── SearchScreen.kt          # Pretraga
├── DodajKnjiguScreen.kt     # Unos nove knjige
├── KnjigaDetaljiSheet.kt    # Prozor sa detaljima knjige
└── Knjiga.kt                # Model podataka + čitanje iz Firebasea
```

## Struktura baze

Svaka knjiga je jedan čvor u Realtime Database:

```json
{
  "naslov": "Opet se vraćam miru i tišini",
  "autori": "Fejzulah Bešo",
  "broj": 160,
  "isbn": "978-9958-9041-8-9",
  "izdavac": "BZK Preporod Vitez",
  "jezik": "bosanski",
  "žanr": "Poezija",
  "cover": "https://.../korice.jpg"
}
```

Polje `cover` (link na sliku korica) je opcionalno. Ako ga knjiga nema, prozor sa detaljima se prikazuje bez slike.

## Pokretanje

### Zahtjevi

- Android Studio (novija verzija)
- JDK 17
- Android uređaj ili emulator

### Koraci

1. Kloniraj repozitorij:
   ```bash
   git clone https://github.com/kemalbeganovic24/Kotlin-eBiblioteka-Android-.git
   ```
2. Otvori projekat u Android Studiju i sačekaj Gradle sync.
3. Dodaj svoj Firebase konfiguracijski fajl **`google-services.json`** u folder `app/`
   (Firebase Console → Project settings → Your apps → Android → Download).
4. Provjeri da `AndroidManifest.xml` sadrži:
   ```xml
   <uses-permission android:name="android.permission.INTERNET"/>
   ```
5. Pokreni aplikaciju (**Run ▶**).

## Napomene

- `google-services.json` sadrži konfiguraciju Firebase projekta, pa ga razmisli ne objavljivati ako je repozitorij javan.
- Za produkciju podesi **Firebase Security Rules**, tako da nepoznati korisnici ne mogu mijenjati bazu.

## Autor

Kemal Beganović
