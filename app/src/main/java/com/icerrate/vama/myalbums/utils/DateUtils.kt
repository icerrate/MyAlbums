package com.icerrate.vama.myalbums.utils

import java.text.ParseException
import java.text.SimpleDateFormat

object DateUtils {

    private const val FORMAT_yyyy_MM_dd = "yyyy-MM-dd"
    private const val FORMAT_MMM_dd_yyyy = "MMM dd, yyyy"

    fun formatStringDate(date: String?): String? {
        return if (date != null && date.isNotEmpty()) {
            try {
                val inFormat = SimpleDateFormat(FORMAT_yyyy_MM_dd);
                val dateObject = inFormat.parse(date);

                val outFormat = SimpleDateFormat(FORMAT_MMM_dd_yyyy);
                outFormat.format(dateObject);
            } catch (e: ParseException) {
                e.printStackTrace();
                null
            }
        } else {
            null
        }
    }
}