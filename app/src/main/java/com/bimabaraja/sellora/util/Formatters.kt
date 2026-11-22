package com.bimabaraja.sellora.util

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

object Formatters {
    fun formatRupiah(amount: Double): String {
        val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        format.maximumFractionDigits = 0
        return format.format(amount)
    }

    fun formatRupiah(amount: Int): String {
        return formatRupiah(amount.toDouble())
    }

    fun formatDateIndonesia(dateString: String?): String {
        if (dateString.isNullOrEmpty()) return "-"
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val date = inputFormat.parse(dateString) ?: return dateString
            val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale("id", "ID"))
            outputFormat.format(date)
        } catch (e: Exception) {
            dateString
        }
    }
}
