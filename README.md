# Anime RPG (Android)

Game RPG sederhana bergaya anime: eksplorasi map grid + battle turn-based.
Dibuat dengan Kotlin + Jetpack Compose.

## Cara dapat APK tanpa laptop (via GitHub Actions)

Kalau kamu cuma punya HP, ikuti langkah ini lewat browser HP:

1. Buat akun di **github.com** (kalau belum punya).
2. Klik **New repository**, kasih nama (misal `AnimeRPG`), set ke **Public**, lalu **Create repository**.
3. Di halaman repo kosong, klik **uploading an existing file**.
4. Extract dulu file `AnimeRPG.zip` ini jadi folder biasa (pakai aplikasi file manager/ZIP di HP), lalu upload **seluruh isi folder** (semua file dan subfolder termasuk folder `.github`) ke GitHub lewat halaman upload tadi. Kalau GitHub web tidak bisa upload folder langsung, upload dulu zip-nya lalu extract via GitHub Codespaces, atau upload satu-satu sesuai struktur folder.
5. Setelah semua file ke-upload dan di-commit, buka tab **Actions** di repo tersebut.
6. GitHub otomatis menjalankan workflow "Build APK" begitu file ter-push. Tunggu sampai selesai (ikon kuning → hijau, biasanya 3-5 menit).
7. Klik hasil run yang sukses (ikon centang hijau), scroll ke bagian **Artifacts**, lalu download **app-debug-apk** (berupa file zip berisi APK).
8. Extract zip tersebut, dapatkan `app-debug.apk`, lalu install ke HP kamu (aktifkan izin "install dari sumber tidak dikenal" kalau diminta).

Kalau workflow gagal (ikon merah), klik run tersebut untuk lihat error log — biasanya karena ada file yang lupa ter-upload.

## Cara menjalankan (kalau punya laptop + Android Studio)

1. Buka **Android Studio** (versi terbaru, minimal Iguana/2023.2+ direkomendasikan).
2. Pilih **Open**, arahkan ke folder `AnimeRPG` (folder yang berisi file `settings.gradle.kts`).
3. Tunggu Gradle sync selesai (Android Studio otomatis mengunduh dependency).
4. Sambungkan HP Android (mode USB debugging aktif) atau buat emulator lewat **Device Manager**.
5. Klik tombol **Run ▶** di toolbar.

## Cara main

- Gunakan tombol panah (▲▼◀▶) untuk bergerak di map.
- Petak **abu-abu gelap** = rumput, jalan di sini punya kemungkinan memicu battle.
- Petak **hijau** = kota (Town), otomatis heal HP/MP penuh saat diinjak.
- Saat battle, pilih salah satu aksi:
  - **Serang**: serangan fisik dasar.
  - **Slash Ki**: skill damage lebih besar, butuh 4 MP.
  - **Heal**: memulihkan sebagian HP, butuh 3 MP.
  - **Lari**: kabur dari battle (kembali ke eksplorasi).
- Menang battle memberi EXP. Kumpulkan EXP untuk naik level (HP/MP/ATK/DEF naik otomatis).
- Jika HP habis, muncul layar Game Over dengan tombol "Mulai Ulang".

## Struktur kode penting

- `game/Models.kt` — data class Player, Enemy, Skill.
- `game/GameMap.kt` — layout peta grid sederhana.
- `game/GameViewModel.kt` — semua logic (gerak, encounter, battle, leveling).
- `game/ExploreScreen.kt`, `game/BattleScreen.kt`, `game/GameOverScreen.kt` — tampilan UI Compose.
- `MainActivity.kt` — entry point, merutekan antar layar.

## Ide pengembangan lanjut

- Tambah lebih banyak map / dungeon.
- Tambah party dengan beberapa karakter.
- Tambah item (potion, equipment).
- Ganti kotak warna dengan sprite/gambar karakter anime asli.
- Simpan progres pakai `SharedPreferences` atau Room database.
