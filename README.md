# Sellora - Aplikasi Manajemen Pesanan dan Rekap Omzet Penjual Live Selling

Sellora adalah aplikasi Android native yang dirancang khusus untuk pelaku UMKM yang berjualan melalui live streaming di platform seperti TikTok dan Shopee. Aplikasi ini mempermudah pencatatan pesanan langsung dari komentar live, pemotongan stok secara otomatis, serta rekapitulasi omzet penjualan per sesi live.

## Teknologi yang Digunakan

- Bahasa Pemrograman: Kotlin
- User Interface: Jetpack Compose & Material 3
- Arsitektur Aplikasi: MVVM (Model-View-ViewModel) dengan StateFlow & UiState
- Navigasi: Navigation Compose (Single NavHost)
- Jaringan & API: Retrofit, Gson Converter, OkHttp Logging Interceptor
- Penyimpanan Lokal: DataStore Preferences (Penyimpanan Token JWT)
- Backend: REST API terpisah dengan database MySQL

## Progres Mingguan

| Minggu | Progres | Status |
| --- | --- | --- |
| Week 1 | Inisialisasi project, konfigurasi Gradle, tema Material 3 kustom, komponen UI reusabel, navigasi utama, serta kerangka layar aplikasi. | Selesai |
| Week 2 | Integrasi jaringan Retrofit, autentikasi JWT login/register, interceptor & authenticator refresh token otomatis, TokenStore DataStore, serta layar Profil. | Selesai |

## Status Fitur Aplikasi

| Fitur | Status |
| --- | --- |
| Login dan Register | Tampilan dan API |
| Kategori | Belum |
| Produk | Belum |
| Sesi Live | Belum |
| Pesanan | Belum |
| Staf | Belum |
| Rekap omzet | Belum |
| File lokal (foto dan ekspor CSV) | Belum |

## Cara Menjalankan Aplikasi

1. Buka project di Android Studio.
2. Pastikan Android SDK sudah terinstal di komputer Anda.
3. Konfigurasi alamat Base URL API pada file `util/Constants.kt` (default `http://10.0.2.2:3000/api/` untuk Emulator Android).
4. Pastikan layanan backend REST API Sellora sudah berjalan pada port 3000.
5. Jalankan aplikasi pada Emulator Android atau perangkat fisik melalui tombol Run di Android Studio.
