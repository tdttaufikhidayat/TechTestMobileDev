package com.taufikhidayat.techtestmobiledev.utils

import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException

fun Throwable.toFriendlyMessage(): String {
    return when (this) {
        is UnknownHostException -> "Tidak ada koneksi internet. Periksa kembali jaringan Anda."
        is IOException -> "Koneksi terputus. Pastikan internet Anda stabil."
        is HttpException -> {
            when (this.code()) {
                429 -> "Batas permintaan (Limit API) harian telah tercapai. Coba lagi besok."
                401 -> "Kunci akses (API Key) tidak valid."
                426 -> "Versi NewsAPI gratis tidak mendukung pencarian sejauh ini."
                else -> "Terjadi kesalahan pada server (Kode: ${this.code()})."
            }
        }
        else -> this.localizedMessage ?: "Terjadi kesalahan yang tidak diketahui."
    }
}
